package io.andrelucas
package toilet.domain.events

import java.time.LocalDateTime
import java.util.UUID

case class ItemAdded(toiletId: UUID, customerId: UUID) extends ToiletEvent(toiletId, 0, LocalDateTime.now()) 
