package io.andrelucas
package toilet.domain.commands

import java.util.UUID

case class CreateToiletOwnerCommand(customerId: UUID, toiletId: UUID)
    
