package io.andrelucas
package toilet.domain.events

import common.domain.event.EventPublisher

trait ToiletEventPublisher extends EventPublisher[ToiletEvent]:
  def publish(event: ToiletEvent): Unit
