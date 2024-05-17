package io.andrelucas
package toilet.domain.events

import java.time.LocalDateTime
import java.util.UUID

case class ToiletRegistered(id: UUID, customerId: UUID) extends ToiletEvent(id, 0, LocalDateTime.now())
