package io.andrelucas

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar


trait UnitTest extends AnyFlatSpecLike
  with Matchers 
  with MockitoSugar with BeforeAndAfterEach
