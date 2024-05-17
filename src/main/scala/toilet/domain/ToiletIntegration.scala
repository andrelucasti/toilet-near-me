package io.andrelucas
package toilet.domain

import toilet.domain.commands.CreateToiletOwnerCommand

trait ToiletIntegration:
  def createToiletOwner(createToiletOwnerCommand: CreateToiletOwnerCommand): Unit

