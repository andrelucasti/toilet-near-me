package io.andrelucas

import org.awaitility.scala.AwaitilitySupport
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.awaitility.Awaitility._


trait UnitTest extends AnyFlatSpecLike
  with AwaitilitySupport
  with Matchers
  with MockitoSugar with BeforeAndAfterEach
