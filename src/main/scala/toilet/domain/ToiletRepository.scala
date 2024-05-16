package io.andrelucas
package toilet.domain

import common.domain.AsyncRepository

import java.util.UUID

trait ToiletRepository extends AsyncRepository[Toilet, UUID]
