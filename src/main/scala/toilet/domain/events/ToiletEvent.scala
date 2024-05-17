package io.andrelucas
package toilet.domain.events

import java.time.LocalDateTime
import java.util.UUID

trait ToiletEvent(aggregatorId: UUID, version: Int, eventDate: LocalDateTime)
