package io.andrelucas
package toilet

class GeolocationTest extends UnitTest {

  it should "return invalid geolocation when latitude is grater than 90 degrees" in {
    Geolocation(90.1, 80).left.e should be (Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(90.5, 80).left.e should be (Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(91.0, 80).left.e should be (Left(GeolocationInvalidException("geolocation is invalid")))
  }

  it should "return invalid geolocation when latitude is less than -90 degrees" in {
    Geolocation(-91.0, 80).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(-90.5, 80).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(-90.1, 80).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
  }

  it should "return invalid geolocation when longitude is grater than 180 degrees" in {
    Geolocation(-77.123, 333).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(-24.522, 333).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(-89.133, 333).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
  }

  it should "return invalid geolocation when longitude is less than -180 degrees" in {
    Geolocation(-77.123, -200).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(-24.522, -181).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
    Geolocation(-89.133, -180.5).left.e should be(Left(GeolocationInvalidException("geolocation is invalid")))
  }

  it should "return the geolocation when latitude and longitude is valid" in {
    val expectedLatitude = -77.123
    val expectedLongitude = -80
    val eitherGeolocation = Geolocation(-77.123, -80)

    eitherGeolocation should be (Geolocation(expectedLatitude, expectedLongitude))
  }
}