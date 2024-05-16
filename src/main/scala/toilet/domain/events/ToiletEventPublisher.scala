package io.andrelucas
package toilet.domain.events

import Toilet.ToiletRegistered

trait ToiletEventPublisher:
  def publish(event: Any): Unit
