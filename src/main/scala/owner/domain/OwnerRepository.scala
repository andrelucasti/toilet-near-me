package io.andrelucas
package owner.domain

import common.domain.AsyncRepository

import java.util.UUID

trait OwnerRepository extends AsyncRepository[Owner, UUID]
