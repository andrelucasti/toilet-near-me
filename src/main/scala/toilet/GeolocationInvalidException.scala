package io.andrelucas

case class GeolocationInvalidException(msg: String, exception: Throwable) extends RuntimeException(msg, exception, false, false)

object GeolocationInvalidException:
  def apply(msg: String): GeolocationInvalidException = 
    new GeolocationInvalidException(msg, null)

  def apply(msg: String, exception: Throwable): GeolocationInvalidException =
    new GeolocationInvalidException(msg, exception)
    
  