package io.andrelucas
package toilet.domain

import common.domain.usecase.UnitUseCase

import io.andrelucas.toilet.domain.RegisterToiletUseCase.Input
import io.andrelucas.toilet.domain.events.ToiletEventPublisher

import java.util.UUID
import scala.util.Try

case class RegisterToiletUseCase(private val toiletRepository: ToiletRepository,
                                 private val toiletEventPublisher: ToiletEventPublisher) extends UnitUseCase[Input]:

  override def execute(input: Input): Try[Unit] = {


    Try{
      val toiletEither = Toilet.register(input.description, input.latitude, input.longitude)

      toiletEither match
        case Left(error) => throw error
        case Right(toilet) =>
          toiletRepository.save(toilet)
          toilet.events.foreach(toiletEventPublisher.publish)
    }
  }

object RegisterToiletUseCase:
  case class Input(id: UUID, description: String, latitude: Double, longitude: Double)
