package io.andrelucas
package toilet

case class ToiletInvalidException(msg: String) extends RuntimeException(msg, null, false, false)
case class ToiletNotFoundException(msg: String) extends RuntimeException(msg, null, false, false)
