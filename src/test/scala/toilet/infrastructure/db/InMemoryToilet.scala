package io.andrelucas
package toilet.infrastructure.db

import common.domain.AsyncRepository

import io.andrelucas.toilet.domain.ToiletRepository

import java.util.UUID
import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class InMemoryToilet extends ToiletRepository {
  
  private val buffer = mutable.Buffer[Toilet]()
  
  override def save(entity: Toilet): Future[Unit] =
    Future{
      buffer.addOne(entity)
    }

  override def update(entity: Toilet): Future[Unit] =
    Future {
      val toilet = buffer.find(_.id == entity.id).get
      buffer -= toilet
      buffer += toilet
    }

  override def findAll(): Future[List[Toilet]] = Future.apply(buffer.toList)

  override def findById(id: UUID): Future[Option[Toilet]] = Future.apply(buffer.find(_.id == id))

  override def exists(id: UUID): Future[Boolean] = Future.apply(buffer.exists(_.id == id))
    
}
