package io.andrelucas
package toilet.domain.usecases

import common.AsyncIntegrationTest
import toilet.domain.events.ToiletEventPublisher
import toilet.domain.{RegisterToiletUseCase, ToiletRepository}
import toilet.infra.db.slick.SlickToiletRepository
import scala.concurrent.ExecutionContext.Implicits.global

import org.scalatest.flatspec.AsyncFlatSpec

import java.util.UUID

class RegisterToiletUseCaseIntegrationTest extends AsyncIntegrationTest {

  var toiletRepository: ToiletRepository = _
  var toiletEventPublisher: ToiletEventPublisher = _
  var subject: RegisterToiletUseCase = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    toiletRepository = new SlickToiletRepository(db)
    toiletEventPublisher = mock[ToiletEventPublisher]

    subject = RegisterToiletUseCase(toiletRepository, toiletEventPublisher)
  }

  it should "register a toilet" in {
    val customerId = UUID.randomUUID()
    val description = "Gym Life Club"
    val latitude = 8.1
    val longitude = 8

    val input = RegisterToiletUseCase.Input(customerId, description, latitude, longitude)
    subject.execute(input)

    val toiletFuture = toiletRepository.findAll()
    toiletFuture.map { toilets =>
      val toilet = toilets.head

      toilet.description should be (description)
      toilet.genre should be (empty)
      toilet.items should be (empty)
      toilet.geolocation.latitude should be (latitude)
      toilet.geolocation.longitude should be (longitude)
    }
  }
}
