package io.andrelucas
package common.domain

import scala.concurrent.Future

trait AsyncRepository[Entity <: Aggregator, ID]:
  def save(entity: Entity): Future[Unit]
  def findAll(): Future[List[Entity]]
  def update(entity: Entity): Future[Unit]
  def findById(id: ID): Future[Option[Entity]]
