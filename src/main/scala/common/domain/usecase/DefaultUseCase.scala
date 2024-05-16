package io.andrelucas
package common.domain.usecase

abstract class DefaultUseCase[INPUT, OUTPUT]:
  def execute(input: INPUT): OUTPUT