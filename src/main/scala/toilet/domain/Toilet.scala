package io.andrelucas

import io.andrelucas.common.domain.Aggregator
import io.andrelucas.toilet.ToiletInvalidException

import java.util.UUID

case class Toilet(id: UUID,
                  description: String,
                  geolocation: Geolocation,
                  items: Set[Items],
                  genre: Set[Genre]) extends Aggregator

object Toilet:
  type RegisterToilet = (String, Double, Double)

  def register(registerToilet: RegisterToilet): Either[Throwable, Toilet] =
    if registerToilet._1.isEmpty then
      Left(ToiletInvalidException("Toilet does not have a valid description"))
    else 
      for 
        geo <- Geolocation(registerToilet._2, registerToilet._3)
      yield
        val toilet = Toilet(UUID.randomUUID(), registerToilet._1, geo, Set.empty[Items], Set.empty[Genre])
        toilet.addEvent(ToiletRegistered(toilet.id))

        toilet
  
  case class ToiletRegistered(id: UUID)
