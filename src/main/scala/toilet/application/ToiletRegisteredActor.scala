package io.andrelucas
package toilet.application

import io.andrelucas.toilet.domain.ToiletIntegration
import io.andrelucas.toilet.domain.commands.CreateToiletOwnerCommand
import io.andrelucas.toilet.domain.events.{ToiletEvent, ToiletRegistered}
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

import java.util.UUID

class ToiletRegisteredActor(context: ActorContext[ToiletEvent], toiletIntegration: ToiletIntegration) extends AbstractBehavior[ToiletEvent](context):
  private def createToiletOwnerCommand:(UUID, UUID) => Unit = (toiletId, customerId) =>
    toiletIntegration.createToiletOwner(CreateToiletOwnerCommand(customerId, toiletId))

  override def onMessage(msg: ToiletEvent): Behavior[ToiletEvent] =
    msg match
      case ToiletRegistered(id, customerId) =>
        context.log.info(s"Toilet Registered - id: $id | customerId: $customerId")
        createToiletOwnerCommand(id, customerId)
        this



object ToiletRegisteredActor:
  def apply(toiletIntegration: ToiletIntegration): Behavior[ToiletEvent] =
    Behaviors.setup(new ToiletRegisteredActor(_, toiletIntegration))

