package io.andrelucas
package toilet.domain

import io.andrelucas.owner.domain.commands.CreateToiletOwnerCommand

trait OwnerIntegration:
  def createToiletOwner(createToiletOwnerCommand: CreateToiletOwnerCommand): Unit

