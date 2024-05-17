package io.andrelucas
package toilet.application

import org.apache.pekko.actor.typed.{Behavior, PostStop, Signal}
import org.apache.pekko.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

class ToiletSupervisor(context: ActorContext[Nothing]) extends AbstractBehavior[Nothing](context):
  context.log.info("Started")

  override def onMessage(msg: Nothing): Behavior[Nothing] =
    Behaviors.unhandled

  override def onSignal: PartialFunction[Signal, Behavior[Nothing]] =
    case PostStop =>
      context.log.info("Stopped")
      this

object ToiletSupervisor:
  def apply(): Behavior[Nothing] =
    Behaviors.setup[Nothing](new ToiletSupervisor(_))