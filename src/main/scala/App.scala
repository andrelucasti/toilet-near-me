package io.andrelucas

import io.andrelucas.AppDeclaration.repository
import io.andrelucas.owner.application.OwnerCommandActor
import io.andrelucas.owner.domain.{CreateOwnerUseCase, OwnerCommand}
import io.andrelucas.toilet.application.{ToiletActor, ToiletEventPublisherImp, ToiletSupervisor}
import io.andrelucas.toilet.domain.{RegisterToiletUseCase, ToiletRepository}
import io.andrelucas.toilet.domain.events.{ToiletEvent, ToiletEventPublisher}
import io.andrelucas.toilet.infra.OwnerPekkoIntegration
import org.apache.pekko.actor.typed.ActorSystem

import java.util.UUID
import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object AppDeclaration {
  val repository: ToiletRepository = new ToiletRepository:
    private val buffer = mutable.Buffer.empty[Toilet]
    override def save(entity: Toilet): Future[Unit] =
      Future{
        buffer.addOne(entity)
      }

    override def findAll(): Future[List[Toilet]] = ???

    override def update(entity: Toilet): Future[Unit] = ???

    override def findById(id: UUID): Future[Toilet] = ???

}


object App:
  def main(args: Array[String]): Unit = {
  val createOwnerUseCase = CreateOwnerUseCase()
  val ownerCommandActor = ActorSystem[OwnerCommand](OwnerCommandActor(createOwnerUseCase), "ownerCommandActor")
  val ownerIntegration = OwnerPekkoIntegration(ownerCommandActor)
    //ActorSystem[Nothing](ToiletSupervisor(ownerIntegration), "toiletSupervisorActor")

  val toiletActor = ActorSystem[ToiletEvent](ToiletActor(ownerIntegration), "toiletActor")
  val toiletEventPublisher: ToiletEventPublisher = ToiletEventPublisherImp(toiletActor)


  val registerToiletUseCase = RegisterToiletUseCase(repository, toiletEventPublisher)

  val registerInput = RegisterToiletUseCase.Input(UUID.randomUUID(), "test", 9.656, -34.900)
  registerToiletUseCase.execute(registerInput)
}
