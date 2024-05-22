package io.andrelucas
package common.domain.event

trait EventPublisher[EVENT]:
  def publish(event: EVENT): Unit
