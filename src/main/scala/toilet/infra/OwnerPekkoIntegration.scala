package io.andrelucas
package toilet.infra

import owner.domain.OwnerCommand
import owner.domain.commands.CreateToiletOwnerCommand
import toilet.domain.OwnerIntegration

import org.apache.pekko.actor.typed.ActorRef

class OwnerPekkoIntegration(ownerActor: ActorRef[OwnerCommand]) extends OwnerIntegration {
  
  override def createToiletOwner(createToiletOwnerCommand: CreateToiletOwnerCommand): Unit = 
    ownerActor ! createToiletOwnerCommand
  
}
