package io.andrelucas
package common.domain.usecase

abstract class NullaryUseCase[OUTPUT]:
  def execute(): OUTPUT