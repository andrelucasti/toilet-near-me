package io.andrelucas
package owner.domain

import common.domain.usecase.UnitUseCase

import io.andrelucas.owner.domain.CreateOwnerUseCase.Input

import java.util.UUID
import scala.util.Try

case class CreateOwnerUseCase() extends UnitUseCase[Input]:
  override def execute(input: Input): Try[Unit] = Try {
    println(s"Creating owner $input")
  }

object CreateOwnerUseCase:
  case class Input(customerId: UUID, toiletId: UUID)
