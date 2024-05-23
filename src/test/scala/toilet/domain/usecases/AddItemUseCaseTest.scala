package io.andrelucas
package toilet.domain.usecases

import io.andrelucas.toilet.domain.ToiletRepository
import io.andrelucas.toilet.domain.events.ToiletEvent
import io.andrelucas.toilet.domain.usecases.AddItemUseCase.Input
import org.awaitility.Awaitility.await
import org.mockito.Mockito.*
import org.mockito.{ArgumentCaptor, Mockito}

import java.util.UUID
import java.util.concurrent.{TimeUnit, TimeoutException}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AddItemUseCaseTest extends UnitTest {
  private val captureAggregator: ArgumentCaptor[Toilet] = ArgumentCaptor.forClass(classOf[Toilet])
  private val captureDomainEvent: ArgumentCaptor[ToiletEvent] = ArgumentCaptor.forClass(classOf[ToiletEvent])

  private var toiletRepository: ToiletRepository = _
  private var subject: AddItemUseCase = _

  override protected def beforeEach(): Unit =
    super.beforeEach()
    toiletRepository = mock[ToiletRepository]
    subject = AddItemUseCase(toiletRepository)

  it should "add a item to a toilet" in {
    val toiletId = UUID.randomUUID()
    val item = Input("soap", toiletId)

    val geo = Geolocation(69.9999, -45.8965).toOption.get
    val toilet = Toilet(toiletId, "Benfica Park", geo, Set.empty, Set.empty)

    when(toiletRepository.findById(toiletId)).thenReturn(Future.apply(Some(toilet)))

    subject.execute(item)
    await atMost(30, TimeUnit.SECONDS) untilAsserted {
      verify(toiletRepository).update(captureAggregator.capture())

      val toiletUpdated = captureAggregator.getValue
      toiletUpdated.id should be(toiletId)
      toiletUpdated.items.map(_.description) should contain(item.description)
    }
  }

  it should "got an error when the toilet still doesn't exists" in {
    val toiletId = UUID.randomUUID()
    val item = Input("soap", toiletId)
    when(toiletRepository.findById(toiletId)).thenReturn(Future.successful(None))

    subject.execute(item)

    await atMost(30, TimeUnit.SECONDS) untilAsserted {
      verify(toiletRepository, never()).update(captureAggregator.capture())
    }
  }
}
