package io.andrelucas
package toilet.domain.events

trait ToiletEventPublisher:
  def publish(event: ToiletEvent): Unit
