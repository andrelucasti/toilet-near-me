package io.andrelucas
package owner.infra.db.slick

import owner.domain.{Owner, OwnerRepository}

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SlickOwnerRepository(db: Database) extends OwnerRepository {
  private val ownerTable = TableQuery[OwnerTable]

  override def save(entity: Owner): Future[Unit] =  {
    val values = (entity.id, entity.customerId, entity.toiletId, 0, java.time.LocalDateTime.now())

    val insert = (for {
      _ <- ownerTable += values
      
    } yield ()).transactionally

    db.run(insert)
  }

  override def findAll(): Future[List[Owner]] =
    val query = ownerTable.map(o=>(o.id, o.customerId, o.toiletId))
      .result
      .transactionally
    
    db.run(query)
      .map(_.map{
        case (id, toiletId, customerId) => 
          Owner(id, customerId, toiletId)
      }.toList)
    

  override def findById(id: UUID): Future[Option[Owner]] = ???

  override def update(entity: Owner): Future[Unit] = ???

}
