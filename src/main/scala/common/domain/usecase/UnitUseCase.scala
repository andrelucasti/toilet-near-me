package io.andrelucas
package common.domain.usecase

import common.domain.Aggregator
import common.domain.event.EventPublisher

import scala.util.Try

abstract class UnitUseCase[INPUT]:
  def execute(input: INPUT): Try[Unit]
  
  def publishEvents[EVENT](aggregator: Aggregator, 
                           eventPublisher: EventPublisher[EVENT]): Unit = 
   
    aggregator.events.foreach(event => eventPublisher.publish(event.asInstanceOf))
  