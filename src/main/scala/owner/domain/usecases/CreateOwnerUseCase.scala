package io.andrelucas
package owner.domain

import common.domain.usecase.UnitUseCase

import io.andrelucas.owner.domain.CreateOwnerUseCase.Input

import java.util.UUID
import scala.util.Try

case class CreateOwnerUseCase(private val ownerRepository: OwnerRepository) extends UnitUseCase[Input]:
  override def execute(input: Input): Try[Unit] = Try {
    val owner = Owner.create(input.customerId, input.toiletId)
    ownerRepository.save(owner)
  }

object CreateOwnerUseCase:
  case class Input(customerId: UUID, toiletId: UUID)
