package com.bynder.boss.healthcheck

import akka.actor.ActorSystem
import akka.actor.typed.scaladsl.adapter._
import akka.event.LoggingAdapter
import akka.persistence.jdbc.testkit.scaladsl.SchemaUtils
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import akka.persistence.jdbc.testkit.scaladsl.SchemaUtils
import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

import java.util.UUID

class JdbcReadinessCheckSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll with ScalaFutures:
  val config                      = ConfigFactory.load()
  val integrationTestMode: String =
    sys.env.getOrElse("BOSS_CORE_RUNTIME_MODE", "DEV")
  val testConfig                  =
    if (integrationTestMode != "DEV") then
      ConfigFactory
        .parseResources("persistence.conf")
        .withFallback(config)
        .resolve
    else
      ConfigFactory
        .parseResources("persistence-non-iam.conf")
        .withFallback(config)
        .resolve

  given system: ActorSystem = ActorSystem("Jdbc-Readiness-Check", testConfig)

  override def beforeAll(): Unit =
    if integrationTestMode == "DEV" then SchemaUtils.createIfNotExists()

  override def afterAll(): Unit =
    system.terminate()

  "Jdbc Readiness" should {
    "check true" in {
      val check = new JdbcReadinessCheck(system)()
      check.futureValue shouldBe true
    }
  }
