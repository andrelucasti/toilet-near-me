package io.andrelucas

import io.andrelucas.toilet.ToiletInvalidException

import java.util.UUID

case class Toilet(id: UUID,
                  description: String,
                  geolocation: Geolocation,
                  items: List[Items],
                  genre: List[Genre])

object Toilet:

  type RegisterToilet = (String, Double, Double)
  def register(registerToilet: RegisterToilet): Either[Throwable, Toilet] =
    if registerToilet._1.isEmpty then 
      Left(ToiletInvalidException("Toilet does not have a valid description")) 
    else 
      for {
        geo <- Geolocation(registerToilet._2, registerToilet._3)
      } yield Toilet(UUID.randomUUID(), registerToilet._1, geo, List.empty[Items], List.empty[Genre])



//    Geolocation(registerToilet._2, registerToilet._3)
//      .flatMap { r =>
//        Right(Toilet(UUID.randomUUID(), registerToilet._1, r, List.empty[Items], List.empty[Genre]))
//      }
