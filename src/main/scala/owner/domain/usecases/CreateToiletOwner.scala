package io.andrelucas
package owner.domain.usecases

import common.domain.usecase.UnitUseCase

import io.andrelucas.owner.domain.usecases.CreateToiletOwner.Input

import scala.util.Try

class CreateToiletOwner extends UnitUseCase[Input]:
  override def execute(input: Input): Try[Unit] = {
    Try{
      
    }
  }

object CreateToiletOwner:
  case class Input()
