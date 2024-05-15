package io.andrelucas

import java.util.UUID

case class Toilet(id: UUID,
                  description: String,
                  geolocation: Geolocation,
                  items: List[Items],
                  genre: List[Genre])

object Toilet:

  type RegisterToilet = (String, Geolocation)
  def register(registerToilet: RegisterToilet): Toilet =
    Toilet(UUID.randomUUID(), registerToilet._1, registerToilet._2, List.empty[Items], List.empty[Genre])