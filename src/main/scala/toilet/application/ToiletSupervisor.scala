package io.andrelucas
package toilet.application

import io.andrelucas.toilet.domain.OwnerIntegration
import io.andrelucas.toilet.domain.events.ToiletEvent
import org.apache.pekko.actor.typed.{Behavior, PostStop, Signal, SupervisorStrategy}
import org.apache.pekko.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

class ToiletSupervisor(context: ActorContext[ToiletEvent], ownerIntegration: OwnerIntegration) extends AbstractBehavior[ToiletEvent](context):
  context.log.info("Started")

  private val supervise = Behaviors.supervise(ToiletActor(ownerIntegration))
    .onFailure(SupervisorStrategy.restart)
  private val toiletActor = context.spawn(supervise, "ToiletActor")

  override def onMessage(msg: ToiletEvent): Behavior[ToiletEvent] =
    toiletActor ! msg
    this

object ToiletSupervisor:
  def apply(ownerIntegration: OwnerIntegration): Behavior[ToiletEvent] =
    Behaviors.setup[ToiletEvent](new ToiletSupervisor(_, ownerIntegration))