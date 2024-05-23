package io.andrelucas
package toilet.domain.usecases

import toilet.domain.events.{ToiletEvent, ToiletEventPublisher, ToiletRegistered}
import toilet.domain.{RegisterToiletUseCase, ToiletRepository}

import org.awaitility.Awaitility.await
import org.mockito.Mockito.*
import org.mockito.{ArgumentCaptor, Mockito}

import java.util.UUID
import java.util.concurrent.TimeUnit
import scala.util.Failure

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
    reset(toiletRepository)
    reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val longitude = 8

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, longitude)
    subject.execute(input)

    verify(toiletRepository).save(captureAggregator.capture())

    val toilet = captureAggregator.getValue
    toilet.description should be (description)
    toilet.genre should be (empty)
    toilet.items should be (empty)
    toilet.geolocation.latitude should be (latitude)
    toilet.geolocation.longitude should be (longitude)
  }

  it should "publish a domain event when a register a toilet" in {
    reset(toiletRepository)
    reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val longitude = 8.32

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, longitude)
    subject.execute(input)

    verify(toiletRepository).save(captureAggregator.capture())

    val toilet = captureAggregator.getValue
    verify(toiletEventPublisher).publish(ToiletRegistered(toilet.id, customerId))
  }

  it should "return an error when latitude is invalid" in {
    reset(toiletRepository)
    reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val invalidLatitude = -92
    val longitude = 8.444

    val input = RegisterToiletUseCase.Input(customerId, description, invalidLatitude, longitude)
    val output = subject.execute(input)

    await atMost(5, TimeUnit.SECONDS) untilAsserted {
      output.isCompleted should be (true)
      output.value match
        case Some(Failure(exception: GeolocationInvalidException)) =>
          exception should be (GeolocationInvalidException("geolocation is invalid"))
        case _ =>
          fail("Expected a GeolocationInvalidException")

      verify(toiletRepository, never()).save(captureAggregator.capture())
      verify(toiletEventPublisher, never()).publish(captureDomainEvent.capture())
    }
  }

  it should "return an error when longitude is invalid" in {
    reset(toiletRepository)
    reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val invalidLongitude = 190.99

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, invalidLongitude)
    val output = subject.execute(input)

    await atMost(5, TimeUnit.SECONDS) untilAsserted{
      output.isCompleted should be (true)
      output.value match
        case Some(Failure(exception: GeolocationInvalidException)) =>
          exception should be (GeolocationInvalidException("geolocation is invalid"))
        case _ =>
          fail("Expected a GeolocationInvalidException")
    }

    verify(toiletRepository, never()).save(captureAggregator.capture())
    verify(toiletEventPublisher, never()).publish(captureDomainEvent.capture())
  }

  it should "return an error when happens some error in the save moment" in {
    reset(toiletRepository)
    reset(toiletEventPublisher)

    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val longitude = 8

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, longitude)

    when(toiletRepository.save(captureAggregator.capture()))
      .thenThrow(RuntimeException("Got an error when saving an toilet"))

    assertThrows[RuntimeException]{
      val output = subject.execute(input)
    }

    verify(toiletEventPublisher, never()).publish(captureDomainEvent.capture())
  }
}
