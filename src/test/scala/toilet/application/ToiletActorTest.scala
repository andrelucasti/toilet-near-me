package io.andrelucas
package toilet.application

import owner.domain.OwnerCommand
import owner.domain.commands.CreateToiletOwnerCommand
import toilet.domain.OwnerIntegration
import toilet.domain.events.ToiletRegistered
import toilet.infra.OwnerPekkoIntegration

import org.apache.pekko.actor.testkit.typed.CapturedLogEvent
import org.apache.pekko.actor.testkit.typed.scaladsl.{BehaviorTestKit, ScalaTestWithActorTestKit}
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.slf4j.event.Level

import java.util.UUID


class ToiletActorTest extends ScalaTestWithActorTestKit with UnitTest {
  private val capture: ArgumentCaptor[CreateToiletOwnerCommand] = ArgumentCaptor.forClass(classOf[CreateToiletOwnerCommand])

  it should "create a toilet owner when a toilet is registered" in {
    val toiletId = UUID.randomUUID()
    val customerId = UUID.randomUUID()
    val ownerIntegration: OwnerIntegration = mock[OwnerIntegration]

    val toiletActor = BehaviorTestKit(ToiletActor(ownerIntegration))
    toiletActor.run(ToiletRegistered(toiletId, customerId))

    verify(ownerIntegration).createToiletOwner(capture.capture())
    val command = capture.getValue
    command should be (CreateToiletOwnerCommand(customerId, toiletId))

    toiletActor.logEntries() should be (Seq(CapturedLogEvent(Level.INFO, s"Toilet Registered - id: $toiletId | customerId: $customerId")))
  }

  it should "send create toilet owner command when a toilet is registered" in {
    val customerId = UUID.randomUUID()
    val toiletId = UUID.randomUUID()

    val ownerCommandActor = testKit.createTestProbe[OwnerCommand]()
    val ownerIntegration: OwnerIntegration = OwnerPekkoIntegration(ownerCommandActor.ref)

    val toiletActor = testKit.spawn(ToiletActor(ownerIntegration))
    toiletActor ! ToiletRegistered(toiletId, customerId)

    ownerCommandActor.expectMessage(CreateToiletOwnerCommand(customerId, toiletId))
  }
}
