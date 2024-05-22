package io.andrelucas
package toilet.infra.db.slick

import slick.jdbc.PostgresProfile.api.*

import java.util.UUID

type ToiletType = (UUID, String, Double, Double, Int, java.time.LocalDateTime)
class ToiletTable(tag: Tag) extends Table[ToiletType](tag, "toilets") {
  def id = column[UUID]("id", O.SqlType("UUID"))
  def description = column[String]("description")
  def latitude = column[Double]("latitude")
  def longitude = column[Double]("longitude")
  def version = column[Int]("version")
  def updatedAt = column[java.time.LocalDateTime]("updated_at", O.Default(java.time.LocalDateTime.now()))

  override def * = (id, description, latitude, longitude, version, updatedAt)
  
}


