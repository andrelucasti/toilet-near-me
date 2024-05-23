package io.andrelucas
package toilet.domain

import common.domain.AsyncRepository

import java.util.UUID
import scala.concurrent.Future

trait ToiletRepository extends AsyncRepository[Toilet, UUID]:
  def exists(id: UUID): Future[Boolean]
