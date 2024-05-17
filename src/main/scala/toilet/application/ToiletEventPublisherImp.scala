package io.andrelucas
package toilet.application

import io.andrelucas.toilet.domain.events.{ToiletEvent, ToiletEventPublisher}
import org.apache.pekko.actor.typed.ActorSystem

class ToiletEventPublisherImp(systemActor: ActorSystem[ToiletEvent]) extends ToiletEventPublisher {
  override def publish(event: ToiletEvent): Unit =
    systemActor ! event
}
