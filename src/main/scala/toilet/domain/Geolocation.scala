package io.andrelucas

import common.ValueObject

case class Geolocation(latitude: Double, longitude: Double) extends ValueObject

object Geolocation:
  def apply(latitude: Double, longitude: Double): Either[GeolocationInvalidException, Geolocation] =
    if(latitude > 90 || latitude < -90) ||
      (longitude > 180 || longitude < -180) then

      Left(GeolocationInvalidException("geolocation is invalid"))
    else
      Right(new Geolocation(latitude, longitude))
  
  def restore(latitude: Double, longitude: Double): Geolocation = 
    Geolocation(latitude, longitude) match 
      case Right(value) => value 
      case Left(exception) => throw exception
    