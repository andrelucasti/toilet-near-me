package io.andrelucas
package common.domain

trait Repository[Entity <: Aggregator, ID]:
  def save(entity: Entity): Unit
  def findAll(): List[Entity]
  def update(entity: Entity): Unit
  def findById(id: ID): Option[Entity]
