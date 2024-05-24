package io.andrelucas
package owner.infrastructure

import owner.domain.{Owner, OwnerRepository}

import java.util.UUID
import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class InMemoryOwner extends OwnerRepository {
  private val buffer = mutable.Buffer[Owner]()

  override def save(entity: Owner): Future[Unit] =
    Future{
      buffer.addOne(entity)
    }

  override def update(entity: Owner): Future[Unit] =
    Future {
      val toilet = buffer.find(_.id == entity.id).get
      buffer -= toilet
      buffer += toilet
    }

  override def findAll(): Future[List[Owner]] = Future.apply(buffer.toList)

  override def findById(id: UUID): Future[Option[Owner]] = Future.apply(buffer.find(_.id == id))
}
