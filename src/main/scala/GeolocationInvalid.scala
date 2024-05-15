package io.andrelucas

case class GeolocationInvalid(msg: String, exception: Throwable) extends RuntimeException(msg, exception, false, false)

object GeolocationInvalid:
  def apply(msg: String): GeolocationInvalid = 
    new GeolocationInvalid(msg, null)

  def apply(msg: String, exception: Throwable): GeolocationInvalid =
    new GeolocationInvalid(msg, exception)
    
  