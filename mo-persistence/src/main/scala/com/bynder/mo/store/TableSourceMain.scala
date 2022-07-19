package com.bynder.mo.store

import java.lang.Long as JLong
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration.*
import akka.Done
import akka.NotUsed
import scala.util.control.NonFatal
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.projection.MergeableOffset
import akka.projection.Projection
import akka.projection.ProjectionBehavior
import akka.projection.ProjectionId
import akka.projection.jdbc.scaladsl.JdbcHandler
import akka.projection.jdbc.scaladsl.JdbcProjection
import akka.projection.kafka.scaladsl.KafkaSourceProvider
import akka.projection.scaladsl.Handler
import akka.projection.scaladsl.SourceProvider
import akka.stream.scaladsl.Source
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import kamon.Kamon

import javax.persistence.EntityManager
import org.slf4j.LoggerFactory

// #imports-producer
import org.apache.kafka.common.serialization.StringSerializer
import akka.kafka.ProducerSettings
// #imports-producer

//#imports
import akka.kafka.ConsumerSettings
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
//#imports

// #sendProducer
import akka.kafka.scaladsl.SendProducer
// #sendProducer

// #producerFlow
import org.apache.kafka.clients.producer.ProducerRecord
import akka.kafka.ProducerMessage
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.FlowWithContext
import akka.projection.ProjectionContext

object TableSourceMain:
  lazy val config =
    ConfigFactory
      .parseString("akka.http.server.preview.enable-http2 = on")
      .withFallback(ConfigFactory.load())
  Kamon.init(config)
  given system: ActorSystem[?] = ActorSystem(Behaviors.empty, "table-source", config)
  given ec: ExecutionContext   = system.executionContext
  lazy val log = system.log

  @main def main(): Unit =
    system.log.info(s"Table Source is running ......")
    sys.addShutdownHook(system.terminate())
    try {
      //init(system)
    } catch {
      case NonFatal(e) =>
        log.error("Terminating due to initialization error", e)
        system.terminate()
    }
