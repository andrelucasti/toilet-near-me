package io.andrelucas
package common.domain.usecase

import scala.util.Try

abstract class UnitUseCase[INPUT]:
  def execute(input: INPUT): Try[Unit] 