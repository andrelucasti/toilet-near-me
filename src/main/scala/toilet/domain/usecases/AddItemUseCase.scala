package io.andrelucas
package toilet.domain.usecases

import common.domain.usecase.UnitUseCase
import toilet.ToiletNotFoundException
import toilet.domain.ToiletRepository
import toilet.domain.usecases.AddItemUseCase.Input

import com.typesafe.scalalogging.Logger

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AddItemUseCase(private val toiletRepository: ToiletRepository) extends UnitUseCase[Input] {
  private val logger = Logger(getClass)

  private def updateToilet: (Input, Toilet) => Future[Unit] = (input, toilet) =>
    toiletRepository.update(toilet.addItem(input.description, input.toiletId))

  private def handleToiletNotFound(toiletId: UUID) =
    val msg = s"The toilet $toiletId wasn't created yet"
    logger.error(msg)
    Future.failed(ToiletNotFoundException(msg))

  override def execute(input: Input): Future[Unit] =
      toiletRepository.findById(input.toiletId).flatMap {
        case Some(toilet) =>
          updateToilet(input, toilet)

        case None =>
          handleToiletNotFound(input.toiletId)

      }.recoverWith {
        case exception =>
          logger.error(s"Got an error to add a new item for ${input.toiletId}", exception)
          Future.failed(exception)
      }
}

object AddItemUseCase:
  case class Input(description: String, toiletId: UUID)

