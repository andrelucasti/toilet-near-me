package io.andrelucas
package owner.domain.usecases

import owner.domain.{CreateOwnerUseCase, Owner, OwnerRepository}
import toilet.domain.ToiletRepository

import org.awaitility.Awaitility.await
import org.mockito.{ArgumentCaptor, Mockito}

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CreateOwnerUseCaseTest extends UnitTest {
  private val captureAggregator: ArgumentCaptor[Owner] = ArgumentCaptor.forClass(classOf[Owner])

  var toiletRepository: ToiletRepository = _
  var ownerRepository: OwnerRepository = _
  var subject: CreateOwnerUseCase = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    toiletRepository = mock[ToiletRepository]
    ownerRepository = mock[OwnerRepository]
    subject = CreateOwnerUseCase(ownerRepository, toiletRepository)
  }

  it should "create a owner" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()

    val input = CreateOwnerUseCase.Input(customerId, toiletId)
    val result = Future.apply(true)

    Mockito.when(toiletRepository.exists(toiletId)).thenReturn(result)
    subject.execute(input)

    await untilAsserted { Mockito.verify(ownerRepository).save(captureAggregator.capture()) }

    val owner = captureAggregator.getValue
    owner.customerId should be (customerId)
    owner.toiletId should be (toiletId)
  }

  it should "not create a owner when the toilet wasn't created yet" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()
    val input = CreateOwnerUseCase.Input(customerId, toiletId)

    val result = Future.apply(false)

    Mockito.when(toiletRepository.exists(toiletId)).thenReturn(result)
    val output = subject.execute(input)

    await untilAsserted { Mockito.verify(ownerRepository, Mockito.never()).save(captureAggregator.capture()) }
    //output.toEither.isLeft should be(true)
  }
}
