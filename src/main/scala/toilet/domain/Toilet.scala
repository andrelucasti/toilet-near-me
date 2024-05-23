package io.andrelucas

import io.andrelucas.common.domain.Aggregator
import io.andrelucas.toilet.ToiletInvalidException
import io.andrelucas.toilet.domain.events.{ItemAdded, ToiletRegistered}

import java.util.UUID

case class Toilet(id: UUID,
                  description: String,
                  geolocation: Geolocation,
                  items: Set[Item],
                  genre: Set[Genre]) extends Aggregator:

  private type AddNewItem = (String, UUID)
  def addItem(addNewItem: AddNewItem): Toilet =
    val itemsUpdated = this.items + Item(UUID.randomUUID(), addNewItem._1)
    val toilet = this.copy(items = itemsUpdated)

    toilet.addEvent(ItemAdded(this.id, addNewItem._2))
    toilet

object Toilet:

  private type RegisterToilet = (UUID, String, Double, Double)
  def register(registerToilet: RegisterToilet): Either[Throwable, Toilet] =
    if registerToilet._2.isEmpty then
      Left(ToiletInvalidException("Toilet does not have a valid description"))
    else
      for
        geo <- Geolocation(registerToilet._3, registerToilet._4)
      yield
        val toilet = Toilet(UUID.randomUUID(), registerToilet._2, geo, Set.empty[Item], Set.empty[Genre])
        toilet.addEvent(ToiletRegistered(toilet.id, registerToilet._1))

        toilet
