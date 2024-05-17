package io.andrelucas
package toilet.application

import io.andrelucas.owner.domain.commands.CreateToiletOwnerCommand
import io.andrelucas.toilet.domain.OwnerIntegration
import io.andrelucas.toilet.domain.events.{ToiletEvent, ToiletRegistered}
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

import java.util.UUID

class ToiletActor(context: ActorContext[ToiletEvent], toiletIntegration: OwnerIntegration) extends AbstractBehavior[ToiletEvent](context):
  context.log.info("Started")
  
  private def createToiletOwnerCommand:(UUID, UUID) => Unit = (toiletId, customerId) =>
    toiletIntegration.createToiletOwner(CreateToiletOwnerCommand(customerId, toiletId))

  override def onMessage(msg: ToiletEvent): Behavior[ToiletEvent] =
    msg match
      case ToiletRegistered(id, customerId) =>
        context.log.info(s"Toilet Registered - id: $id | customerId: $customerId")
        createToiletOwnerCommand(id, customerId)
        this



object ToiletActor:
  def apply(toiletIntegration: OwnerIntegration): Behavior[ToiletEvent] =
    Behaviors.setup(new ToiletActor(_, toiletIntegration))

