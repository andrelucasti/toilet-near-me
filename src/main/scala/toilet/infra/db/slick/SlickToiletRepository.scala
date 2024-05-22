package io.andrelucas
package toilet.infra.db.slick

import toilet.domain.ToiletRepository
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SlickToiletRepository(db: Database) extends ToiletRepository {
  private val toiletTable = TableQuery[ToiletTable]

  override def save(entity: Toilet): Future[Unit] = {
    val values = (entity.id, entity.description, entity.geolocation.latitude, entity.geolocation.longitude, 0, java.time.LocalDateTime.now())
    
    val insert = (for {
      _ <- toiletTable += values
    } yield ()).transactionally

    db.run(insert)
  }

  override def findById(id: UUID): Future[Toilet] = ???

  override def findAll(): Future[List[Toilet]] =
    val query = toiletTable.map(t =>(t.id, t.description, t.latitude, t.longitude))
      .result
      .transactionally

    db.run(query).map(_.map {
      case (id, description, latitude, longitude) =>
        val geo = Geolocation(latitude, longitude) match
          case Right(value) => value
          case Left(exception) => throw RuntimeException(exception)
          
        Toilet(id, description, geo, Set.empty, Set.empty)
    }.toList)
  
  override def update(entity: Toilet): Future[Unit] = ???
}
