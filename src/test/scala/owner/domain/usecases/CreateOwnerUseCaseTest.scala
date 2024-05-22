package io.andrelucas
package owner.domain.usecases

import owner.domain.{CreateOwnerUseCase, Owner, OwnerRepository}

import org.mockito.{ArgumentCaptor, Mockito}

import java.util.UUID

class CreateOwnerUseCaseTest extends UnitTest {
  private val captureAggregator: ArgumentCaptor[Owner] = ArgumentCaptor.forClass(classOf[Owner])

  var subject: CreateOwnerUseCase = _
  var ownerRepository: OwnerRepository = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    ownerRepository = mock[OwnerRepository]
    subject = CreateOwnerUseCase(ownerRepository)
  }

  it should "create a owner" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()

    val input = CreateOwnerUseCase.Input(customerId, toiletId)
    subject.execute(input)

    Mockito.verify(ownerRepository).save(captureAggregator.capture())
    val owner = captureAggregator.getValue

    owner.customerId should be (customerId)
    owner.toiletId should be (toiletId)
  }

  it should "create a owner when the customer already is created" in {

  }

  it should "create a owner when the toilet already is created" in {

  }
}
