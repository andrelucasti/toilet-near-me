package io.andrelucas
package toilet.domain.usecases

import toilet.domain.ToiletRepository
import toilet.domain.events.ToiletEvent
import toilet.domain.usecases.AddItemUseCase.Input

import io.andrelucas.common.AsyncIntegrationTest
import io.andrelucas.toilet.infra.db.slick.SlickToiletRepository
import io.andrelucas.toilet.infrastructure.db.InMemoryToilet
import org.awaitility.Awaitility.await
import org.mockito.Mockito.*
import org.mockito.{ArgumentCaptor, Mockito}

import java.util.UUID
import java.util.concurrent.{TimeUnit, TimeoutException}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AddItemUseCaseIntegrationTest extends AsyncIntegrationTest {

  private var toiletRepository: ToiletRepository = _
  private var subject: AddItemUseCase = _

  override protected def beforeEach(): Unit =
    super.beforeEach()
    
    toiletRepository = new SlickToiletRepository(db)
    subject = AddItemUseCase(toiletRepository)

  it should "add a item to a toilet" in {
    val toiletId = UUID.randomUUID()
    val geo = Geolocation(69.9999, -45.8965).toOption.get
    val toilet = Toilet(toiletId, "Benfica Park", geo, Set.empty, Set.empty)

    val saveUnit = toiletRepository.save(toilet)
    await untilAsserted {
      saveUnit.isCompleted should be (true)
    }

    val item = Input("soap", toiletId)
    val eventualUnit = subject.execute(item)

    await untilAsserted{
      eventualUnit.isCompleted should be (true)
    }

    val toiletUpdated = toiletRepository.findById(toiletId)

    await atMost(5, TimeUnit.SECONDS) untilAsserted {
        toiletUpdated.map {
          case Some(toiletUpdated) =>
            toiletUpdated.id should be(toiletId)
            toiletUpdated.description should be(toilet.description)
            toiletUpdated.geolocation should be(toilet.geolocation)
            toiletUpdated.items.map(_.description) should contain("soap")
          case None =>
            fail("expected a toilet")
        }
      }
    }
}
