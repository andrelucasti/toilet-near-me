package io.andrelucas
package common.domain

import common.Entity

import scala.collection.mutable

abstract class Aggregator extends Entity:
  val events: mutable.Set[Any] = mutable.Set.empty
  
  def addEvent[Event](event: Event) = {
    events += event
  }
