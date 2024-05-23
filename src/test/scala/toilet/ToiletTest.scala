package io.andrelucas
package toilet

import io.andrelucas.toilet.domain.events.{ItemAdded, ToiletRegistered}
import org.scalatest.matchers.must.Matchers.not

import java.util.UUID
import scala.collection.immutable.Set

class ToiletTest extends UnitTest {
  
  it should "return error when try create a toilet Geolocation invalid" in {
    Toilet.register(UUID.randomUUID(), "Benfica Stadium", -100, -100).left.e should be (Left(GeolocationInvalidException("geolocation is invalid")))
  }

  it should "return error when create a toilet with description empty" in {
    Toilet.register(UUID.randomUUID(), "", -8.55, 11.2).left.e should be (Left(ToiletInvalidException("Toilet does not have a valid description")))
  }

  it should "return a toilet" in {
    val customerId = UUID.randomUUID()
    val expectedDescription = "Benfica Stadium"
    val expectedGeolocation = Geolocation(-8.55, 11.2)

    val toilet = Toilet.register(customerId, "Benfica Stadium", -8.55, 11.2).toOption.get

    toilet.genre should be (empty)
    toilet.items should be (empty)
    toilet.id should not be Nil
    toilet.geolocation should be (expectedGeolocation.toOption.get)
    toilet.description should be (expectedDescription)
  }

  it should "save a domain event when a toilet is created" in {
    val customerId = UUID.randomUUID()
    val toilet = Toilet.register(customerId, "Benfica Stadium", -8.55, 11.2)

    for {
      toiletEvent <- toilet

    } yield
      Right(toiletEvent.id) should be (toilet.map(_.id))
      toiletEvent.events should contain (ToiletRegistered(toiletEvent.id, customerId))
  }

  it should "add a item" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()

    val geo = Geolocation(69.9999, -45.8965).toOption.get
    val toilet = Toilet(toiletId, "Benfica Stadium", geo, Set.empty, Set.empty)

    val toiletUpdated = toilet.addItem("soap", customerId)

    toiletUpdated.items.map(_.description) should contain ("soap")
  }
  
  it should "save domain event when a item is added" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()

    val geo = Geolocation(69.9999, -45.8965).toOption.get
    val toilet = Toilet(toiletId, "Benfica Stadium", geo, Set.empty, Set.empty)

    val toiletUpdated = toilet.addItem("soap", customerId)
    
    toiletUpdated.events should contain (ItemAdded(toiletId, customerId))
  }
}
