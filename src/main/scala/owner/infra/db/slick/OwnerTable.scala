package io.andrelucas
package owner.infra.db.slick

import slick.jdbc.PostgresProfile.api.*

import java.util.UUID

type OwnerType = (UUID, UUID, UUID, Int, java.time.LocalDateTime)
class OwnerTable(tag: Tag) extends Table[OwnerType](tag, "owners"){
  def id = column[UUID]("id", O.SqlType("UUID"))
  def customerId = column[UUID]("customer_id")
  def toiletId = column[UUID]("toilet_id")
  def version = column[Int]("version")
  def updatedAt = column[java.time.LocalDateTime]("updated_at", O.Default(java.time.LocalDateTime.now()))

  override def * = (id, customerId, toiletId, version, updatedAt)
}
