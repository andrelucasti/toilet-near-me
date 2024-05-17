package io.andrelucas

import io.andrelucas.toilet.application.ToiletSupervisor
import org.apache.pekko.actor.typed.ActorSystem


object App:
  def main(args: Array[String]): Unit = {
    ActorSystem[Nothing](ToiletSupervisor(), "Toilet")
  }
