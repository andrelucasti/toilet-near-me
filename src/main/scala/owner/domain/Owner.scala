package io.andrelucas
package owner.domain

import common.domain.Aggregator

import java.util.UUID

case class Owner(id:UUID, 
                 toiletId: UUID, 
                 customerId: UUID) extends Aggregator

object Owner:
  def create: (UUID, UUID) => Owner = (customerId, toiletId) => Owner(UUID.randomUUID(), toiletId, customerId)
