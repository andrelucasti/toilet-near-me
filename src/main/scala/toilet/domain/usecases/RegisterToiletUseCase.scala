package io.andrelucas
package toilet.domain

import common.domain.usecase.UnitUseCase

import com.typesafe.scalalogging.Logger
import io.andrelucas.toilet.domain.RegisterToiletUseCase.Input
import io.andrelucas.toilet.domain.events.{ToiletEvent, ToiletEventPublisher}

import java.util.UUID
import scala.util.Try

case class RegisterToiletUseCase(private val toiletRepository: ToiletRepository,
                                 private val toiletEventPublisher: ToiletEventPublisher) extends UnitUseCase[Input]:

  private val logger = Logger(RegisterToiletUseCase.getClass.getName)

  override def execute(input: Input): Try[Unit] = {

    Try{
      val toiletEither = Toilet.register(input.customerId, input.description, input.latitude, input.longitude)

      toiletEither match
        case Left(error) =>
          logger.error("Got an error at to register a toilet", error)
          throw error

        case Right(toilet) =>
          toiletRepository.save(toilet)
         
          publishEvents[ToiletEvent](toilet, toiletEventPublisher)
    }
  }

object RegisterToiletUseCase:
  case class Input(customerId: UUID, description: String, latitude: Double, longitude: Double)
