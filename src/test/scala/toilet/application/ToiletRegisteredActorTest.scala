package io.andrelucas
package toilet.application

import io.andrelucas.toilet.domain.ToiletIntegration
import io.andrelucas.toilet.domain.commands.CreateToiletOwnerCommand
import io.andrelucas.toilet.domain.events.ToiletRegistered
import org.apache.pekko.actor.testkit.typed.CapturedLogEvent
import org.apache.pekko.actor.testkit.typed.scaladsl.BehaviorTestKit
import org.apache.pekko.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.mockito.{ArgumentCaptor, Mockito}
import org.slf4j.event.Level

import java.util.UUID


class ToiletRegisteredActorTest extends ScalaTestWithActorTestKit with UnitTest {
  private val capture: ArgumentCaptor[CreateToiletOwnerCommand] = ArgumentCaptor.forClass(classOf[CreateToiletOwnerCommand])
  it should "create a toilet owner when a toilet is registered" in {
    val toiletIntegration: ToiletIntegration = mock[ToiletIntegration]
    val toiletRegisteredActor = BehaviorTestKit(ToiletRegisteredActor(toiletIntegration))

    val toiletId = UUID.randomUUID()
    val customerId = UUID.randomUUID()
    //toiletRegisteredActor ! ToiletRegistered(toiletId, customerId)
    toiletRegisteredActor.run(ToiletRegistered(toiletId, customerId))

    Mockito.verify(toiletIntegration).createToiletOwner(capture.capture())
    val command = capture.getValue
    command should be (CreateToiletOwnerCommand(customerId, toiletId))

    toiletRegisteredActor.logEntries() should be (Seq(CapturedLogEvent(Level.INFO, s"Toilet Registered - id: $toiletId | customerId: $customerId")))
  }
}
