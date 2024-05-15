package io.andrelucas
package toilet

case class ToiletInvalidException(msg: String) extends RuntimeException(msg, null, false, false)
