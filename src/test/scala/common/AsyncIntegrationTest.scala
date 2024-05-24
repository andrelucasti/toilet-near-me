package io.andrelucas
package common

import owner.infra.db.slick.OwnerTable
import toilet.infra.db.slick.{ItemTable, ToiletTable}

import org.awaitility.scala.AwaitilitySupport
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

import scala.concurrent.ExecutionContext.Implicits.global

trait AsyncIntegrationTest extends AnyFlatSpec 
  with Matchers 
  with AwaitilitySupport
  with MockitoSugar 
  with BeforeAndAfterEach {

  val db = Database.forConfig("toiletdb")
  val toiletTable = TableQuery[ToiletTable]
  val itemTable = TableQuery[ItemTable]
  val ownerTable = TableQuery[OwnerTable]

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    val action = (for{
      _ <- ownerTable.delete
      _ <- itemTable.delete
      _ <- toiletTable.delete

    }yield()).transactionally

    db.run(action)
  }
}
  
