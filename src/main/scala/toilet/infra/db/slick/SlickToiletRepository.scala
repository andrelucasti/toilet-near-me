package io.andrelucas
package toilet.infra.db.slick

import toilet.domain.ToiletRepository

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

import java.time.LocalDateTime
import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SlickToiletRepository(db: Database) extends ToiletRepository {
  private val toiletTable = TableQuery[ToiletTable]
  private val itemTable = TableQuery[ItemTable]

  override def save(entity: Toilet): Future[Unit] = {
    val values = (entity.id, entity.description, entity.geolocation.latitude, entity.geolocation.longitude, 0, java.time.LocalDateTime.now())
    
    val insert = (for {
      _ <- toiletTable += values
    } yield ()).transactionally

    db.run(insert)
  }

  override def findById(id: UUID): Future[Option[Toilet]] =
    val query = toiletTable
      .filter(_.id === id)
      .map(t => (t.id, t.description, t.latitude, t.longitude))
      .result
      .headOption
      .transactionally
    
    db.run(query).flatMap{
      case Some((id, description, latitude, longitude)) =>
        val geo = Geolocation.restore(latitude, longitude)
        Future.successful(Some(Toilet(id, description, geo, Set.empty, Set.empty)))
      case None => 
        Future.successful(None)
    }


  override def findAll(): Future[List[Toilet]] =
    val query = toiletTable.map(t =>(t.id, t.description, t.latitude, t.longitude))
      .result
      .transactionally

    db.run(query).map(_.map {
      case (id, description, latitude, longitude) =>
        val geo = Geolocation.restore(latitude, longitude)
        Toilet(id, description, geo, Set.empty, Set.empty)
    }.toList)

  override def update(entity: Toilet): Future[Unit] =
    val action = (for{
      currentVersion <- toiletTable.filter(_.id===entity.id).map(_.version).result.headOption
      newVersion = currentVersion.map(_ + 1).getOrElse(0)

      _ <- toiletTable.filter(_.id===entity.id)
        .map(t => (t.description, t.latitude, t.longitude, t.version, t.updatedAt))
        .update(entity.description, entity.geolocation.latitude, entity.geolocation.longitude, newVersion, LocalDateTime.now())

      items = for
        item <- entity.items
        itemsValue = (item.id, item.description, 0, LocalDateTime.now(), entity.id)
      yield itemsValue

      _ <- itemTable ++= items

    } yield()).transactionally

    db.run(action)

  override def exists(id: UUID): Future[Boolean] =
    val query = toiletTable.filter(_.id === id).exists.result
    
    db.run(query)
}
