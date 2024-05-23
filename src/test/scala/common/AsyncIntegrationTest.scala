package io.andrelucas
package common

import io.andrelucas.owner.infra.db.slick.OwnerTable
import io.andrelucas.toilet.infra.db.slick.ToiletTable
import org.awaitility.scala.AwaitilitySupport
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.{AnyFlatSpec, AsyncFlatSpec}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

trait AsyncIntegrationTest extends AnyFlatSpec 
  with Matchers 
  with ScalaFutures 
  with AwaitilitySupport
  with MockitoSugar 
  with BeforeAndAfterEach {

  val db = Database.forConfig("toiletdb")
  val toiletTable = TableQuery[ToiletTable]
  val ownerTable = TableQuery[OwnerTable]

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    db.run(ownerTable.delete.transactionally)
    db.run(toiletTable.delete.transactionally)
  }
}
  
