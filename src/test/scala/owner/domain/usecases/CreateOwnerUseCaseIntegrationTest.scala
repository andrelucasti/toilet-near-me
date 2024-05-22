package io.andrelucas
package owner.domain.usecases

import owner.domain.{CreateOwnerUseCase, Owner, OwnerRepository}

import io.andrelucas.common.AsyncIntegrationTest
import io.andrelucas.owner.infra.db.slick.SlickOwnerRepository
import org.mockito.{ArgumentCaptor, Mockito}

import java.util.UUID

class CreateOwnerUseCaseIntegrationTest extends AsyncIntegrationTest {

  var subject: CreateOwnerUseCase = _
  var ownerRepository: OwnerRepository = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    ownerRepository = new SlickOwnerRepository(db)
    subject = CreateOwnerUseCase(ownerRepository)
  }

  it should "create a owner" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()

    val input = CreateOwnerUseCase.Input(customerId, toiletId)
    subject.execute(input)

    val ownerFuture = ownerRepository.findAll()
    
    ownerFuture.map { owners =>
      val owner = owners.head
      
      owner.customerId should be (customerId)
      owner.toiletId should be (toiletId)
    }
  }
}
