package io.andrelucas
package owner.domain

import common.domain.usecase.UnitUseCase
import owner.domain.CreateOwnerUseCase.Input
import toilet.domain.ToiletRepository

import com.typesafe.scalalogging.Logger

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class CreateOwnerUseCase(private val ownerRepository: OwnerRepository, 
                              private val toiletRepository: ToiletRepository) extends UnitUseCase[Input]:
  private val logger = Logger(CreateOwnerUseCase.getClass.getName)

  override def execute(input: Input): Future[Unit] = {

    for
      toiletExists <- toiletRepository.exists(input.toiletId)
    yield
      if toiletExists then
        val owner = Owner.create(input.customerId, input.toiletId)
        ownerRepository.save(owner)
      else
        logger.error(s"Toilet ${input.toiletId} wasn't created yet")
        throw java.lang.RuntimeException(s"Toilet ${input.toiletId} wasn't created yet")
  }

object CreateOwnerUseCase:
  case class Input(customerId: UUID, toiletId: UUID)
