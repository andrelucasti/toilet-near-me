package io.andrelucas
package owner.domain.usecases

import owner.domain.{CreateOwnerUseCase, Owner, OwnerRepository}

import io.andrelucas.common.AsyncIntegrationTest
import io.andrelucas.owner.infra.db.slick.SlickOwnerRepository
import io.andrelucas.owner.infrastructure.InMemoryOwner
import io.andrelucas.toilet.domain.ToiletRepository
import io.andrelucas.toilet.infra.db.slick.SlickToiletRepository
import io.andrelucas.toilet.infrastructure.db.InMemoryToilet
import org.awaitility.Awaitility.await
import org.mockito.{ArgumentCaptor, Mockito}

import scala.concurrent.ExecutionContext.Implicits.global
import java.time.Duration
import java.util.UUID

class CreateOwnerUseCaseIntegrationTest extends AsyncIntegrationTest {

  var subject: CreateOwnerUseCase = _
  var ownerRepository: OwnerRepository = _
  var toiletRepository: ToiletRepository = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    ownerRepository = new SlickOwnerRepository(db)
    toiletRepository = new SlickToiletRepository(db)
    subject = CreateOwnerUseCase(ownerRepository, toiletRepository)
  }

  it should "create a owner" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()

    val geo = Geolocation(8.567, -9.342).toOption.get
    val toilet = Toilet(toiletId, "toilet", geo, Set.empty, Set.empty)
    toiletRepository.save(toilet)

    val input = CreateOwnerUseCase.Input(customerId, toiletId)
    subject.execute(input)
    
    val ownerFuture = ownerRepository.findAll()
    
    await atMost Duration.ofMinutes(60) untilAsserted{
      ownerFuture.foreach { o =>
        val owner = o.head
        owner.customerId should be(customerId)
        owner.toiletId should be(toiletId)
      }
    }
  }
}
