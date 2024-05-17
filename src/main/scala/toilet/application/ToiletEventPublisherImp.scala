package io.andrelucas
package toilet.application

import io.andrelucas.toilet.domain.events.{ToiletEvent, ToiletEventPublisher}
import org.apache.pekko.actor.typed.{ActorRef, ActorSystem}

class ToiletEventPublisherImp(toiletActor: ActorRef[ToiletEvent]) extends ToiletEventPublisher {
  override def publish(event: ToiletEvent): Unit =
    toiletActor ! event
}
