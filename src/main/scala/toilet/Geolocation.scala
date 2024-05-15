package io.andrelucas

case class Geolocation(latitude: Double, longitude: Double)
object Geolocation:
  def apply(latitude: Double, longitude: Double): Either[GeolocationInvalid, Geolocation] =
    if(latitude > 90 || latitude < -90) ||
      (longitude > 180 || longitude < -180) then

      Left(GeolocationInvalid("geolocation is invalid"))
    else
      Right(new Geolocation(latitude, longitude))