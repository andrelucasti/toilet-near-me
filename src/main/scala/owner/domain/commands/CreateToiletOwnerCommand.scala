package io.andrelucas
package owner.domain.commands

import io.andrelucas.owner.domain.OwnerCommand

import java.util.UUID

case class CreateToiletOwnerCommand(customerId: UUID, toiletId: UUID) extends OwnerCommand
    
