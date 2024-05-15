package io.andrelucas
package toilet

import org.scalatest.matchers.must.Matchers.not

class ToiletTest extends Unit {
  
  it should "return error when try create a toilet Geolocation invalid" in {
    Toilet.register("Benfica Stadium", -100, -100).left.e should be (Left(GeolocationInvalidException("geolocation is invalid")))
  }

  it should "return error when create a toilet with description empty" in {
    Toilet.register("", -8.55, 11.2).left.e should be (Left(ToiletInvalidException("Toilet does not have a valid description")))
  }

  it should "return a toilet" in {
    val expectedDescription = "Benfica Stadium"
    val expectedGeolocation = Geolocation(-8.55, 11.2)

    val toilet = Toilet.register("Benfica Stadium", -8.55, 11.2).toOption.get

    toilet.genre should be (empty)
    toilet.items should be (empty)
    toilet.id should not be Nil
    toilet.geolocation should be (expectedGeolocation.toOption.get)
    toilet.description should be (expectedDescription)
  }
}
