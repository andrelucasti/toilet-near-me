package io.andrelucas
package toilet.application

import io.andrelucas.toilet.domain.OwnerIntegration
import org.apache.pekko.actor.typed.{Behavior, PostStop, Signal, SupervisorStrategy}
import org.apache.pekko.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

class ToiletSupervisor(context: ActorContext[Nothing], ownerIntegration: OwnerIntegration) extends AbstractBehavior[Nothing](context):
  context.log.info("Started")

  private val supervise = Behaviors.supervise(ToiletActor(ownerIntegration)).onFailure(SupervisorStrategy.restart)
  context.spawn(supervise, "ToiletActor")

  override def onMessage(msg: Nothing): Behavior[Nothing] =
    Behaviors.unhandled

  override def onSignal: PartialFunction[Signal, Behavior[Nothing]] =
    case PostStop =>
      context.log.info("Stopped")
      this

object ToiletSupervisor:
  def apply(ownerIntegration: OwnerIntegration): Behavior[Nothing] =
    Behaviors.setup[Nothing](new ToiletSupervisor(_, ownerIntegration))