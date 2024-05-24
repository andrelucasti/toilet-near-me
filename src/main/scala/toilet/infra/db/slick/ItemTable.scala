package io.andrelucas
package toilet.infra.db.slick

import java.util.UUID
import slick.jdbc.PostgresProfile.api.*

type ItemType = (UUID, String, Int, java.time.LocalDateTime, UUID)
class ItemTable(tag: Tag) extends Table[ItemType](tag, "items"){
  def id = column[UUID]("id", O.SqlType("UUID"))
  def description = column[String]("description")
  def version = column[Int]("version")
  def updatedAt = column[java.time.LocalDateTime]("updated_at", O.Default(java.time.LocalDateTime.now()))
  def toiletId = column[UUID]("toilet_id")

  override def * = (id, description, version, updatedAt, toiletId)
}
