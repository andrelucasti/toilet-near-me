package io.andrelucas
package owner.application

import owner.domain.{CreateOwnerUseCase, OwnerCommand}

import io.andrelucas.owner.domain.commands.CreateToiletOwnerCommand
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

class OwnerCommandActor(context: ActorContext[OwnerCommand],
                        createOwnerUseCase: CreateOwnerUseCase) extends AbstractBehavior[OwnerCommand](context){

  context.log.info("Started")

  override def onMessage(msg: OwnerCommand): Behavior[OwnerCommand] =
    msg match
      case CreateToiletOwnerCommand(customerId, toiletId) =>
        val ownerRef = context.spawn(Behaviors.empty[String], "ownerChild")
        context.log.info(s"Creating owner $customerId | $toiletId - $ownerRef")

        createOwnerUseCase.execute(CreateOwnerUseCase.Input(customerId, toiletId))
        this
}

object OwnerCommandActor:
  def apply(createOwnerUseCase: CreateOwnerUseCase): Behavior[OwnerCommand] =
    Behaviors.setup(new OwnerCommandActor(_, createOwnerUseCase))