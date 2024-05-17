package io.andrelucas
package toilet.domain.usecases

import toilet.domain.{RegisterToiletUseCase, ToiletRepository}

import io.andrelucas.toilet.domain.events.{ToiletEvent, ToiletEventPublisher, ToiletRegistered}
import org.mockito.{ArgumentCaptor, Mockito}

import java.util.UUID

class RegisterToiletUseCaseTest extends UnitTest {
  private val captureAggregator: ArgumentCaptor[Toilet] = ArgumentCaptor.forClass(classOf[Toilet])
  private val captureDomainEvent: ArgumentCaptor[ToiletEvent] = ArgumentCaptor.forClass(classOf[ToiletEvent])

  var toiletRepository: ToiletRepository = _
  var toiletEventPublisher: ToiletEventPublisher = _
  var subject: RegisterToiletUseCase = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    toiletRepository = mock[ToiletRepository]
    toiletEventPublisher = mock[ToiletEventPublisher]

    subject = RegisterToiletUseCase(toiletRepository, toiletEventPublisher)
  }

  it should "register a toilet" in {
    Mockito.reset(toiletRepository)
    Mockito.reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val longitude = 8

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, longitude)
    subject.execute(input)

    Mockito.verify(toiletRepository).save(captureAggregator.capture())

    val toilet = captureAggregator.getValue
    toilet.description should be (description)
    toilet.genre should be (empty)
    toilet.items should be (empty)
    toilet.geolocation.latitude should be (latitude)
    toilet.geolocation.longitude should be (longitude)
  }

  it should "publish a domain event when a register a toilet" in {
    Mockito.reset(toiletRepository)
    Mockito.reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val longitude = 8.32

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, longitude)
    subject.execute(input)

    Mockito.verify(toiletRepository).save(captureAggregator.capture())

    val toilet = captureAggregator.getValue
    Mockito.verify(toiletEventPublisher).publish(ToiletRegistered(toilet.id, customerId))
  }

  it should "return an error when latitude is invalid" in {
    Mockito.reset(toiletRepository)
    Mockito.reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val invalidLatitude = -92
    val longitude = 8.444

    val input = RegisterToiletUseCase.Input(customerId, description, invalidLatitude, longitude)
    val output = subject.execute(input)

    output.toEither.left.e should be (Left(GeolocationInvalidException("geolocation is invalid")))
    Mockito.verify(toiletRepository, Mockito.never()).save(captureAggregator.capture())
    Mockito.verify(toiletEventPublisher, Mockito.never()).publish(captureDomainEvent.capture())
  }

  it should "return an error when longitude is invalid" in {
    Mockito.reset(toiletRepository)
    Mockito.reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val invalidLongitude = 190.99

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, invalidLongitude)
    val output = subject.execute(input)

    output.toEither.left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
    Mockito.verify(toiletRepository, Mockito.never()).save(captureAggregator.capture())
    Mockito.verify(toiletEventPublisher, Mockito.never()).publish(captureDomainEvent.capture())
  }

  it should "return an error when happens some error in the save moment" in {
    Mockito.reset(toiletRepository)
    Mockito.reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val longitude = 8

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, longitude)

    Mockito.when(toiletRepository.save(captureAggregator.capture())).thenThrow(new RuntimeException("Got an error when saving an toilet"))

    val output = subject.execute(input)
    Mockito.verify(toiletEventPublisher, Mockito.never()).publish(captureDomainEvent.capture())
  }
}
