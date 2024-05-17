package io.andrelucas
package owner.domain

import common.domain.Aggregator

import java.util.UUID

case class Owner(id:UUID, toiletId: UUID, customerId: UUID) extends Aggregator
