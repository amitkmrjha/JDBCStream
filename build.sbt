import com.typesafe.sbt.packager.docker._
import BuildSettings._
import Dependencies._

val commonDeps          = Seq(scalaTest, scalaCheck, logback, logbackJson, logbackJackson)
val akkaDeps            = Seq(akkaTyped, akkaSLF4J, akkaTypedTestKit)
val gatlingDeps         = Seq(gatlingHighCharts, gatlingTestFramework)
val akkaHttpDeps        =
  Seq(
    akkaHttp,
    akkaHttpXml,
    sprayJson,
    akkaHttpSprayJson,
    akkaHttpTestKit,
    swaggerAkkaHttp,
    swaggerAkkaHttpScala,
    swaggerAkkaHttpEnum,
    jacksonModuleScala,
    jakartaWsRs,
    swaggerCore
  )
val akkaStreamsDeps     = Seq(akkaStreams, akkaStreamsTestKit)
val akkaClusterDeps     = Seq(
  akkaClusterShardingTyped,
  akkaDiscovery,
  akkaDiscoveryKubernetes,
  akkaManagement,
  akkaManagementClusterBootstrap,
  akkaManagementClusterHTTP,
  akkaHttpSprayJson
)
val akkaPersistenceDeps =
  Seq(
    akkaPersistence,
    akkaPersistenceTestKit,
    akkaPersistenceJdbc,
    akkaPersistenceQuery,
    akkaJackson,
    jacksonCore,
    jacksonModuleScala,
    postgres,
    iamPostgresJDBCDriver
  )

val akkaProjectionDeps = Seq(
  alpakkaKafka,
  akkaPersistenceEventSourcedProjection,
  akkaPersistenceJDBCProjection,
  scalikeJDBC,
  scalikeJDBCConfig,
  sslConfig
)
val s3Deps             =
  Seq(
    alpakkaS3,
    scalapbRuntime,
    awsS3,
    awsSTS,
    akkaHttpXml,
    scalaXml
  )

val monitoringDeps = Seq(kamonBundle, kamonPrometheus)

val excludeProtobufConflictDeps = Seq(
  ExclusionRule(
    "com.thesamet.scalapb",
    "scalapb-runtime_3"
  ),
  ExclusionRule(
    "org.scala-lang.modules",
    "scala-collection-compat_3"
  )
)

val excludeXmlConflictDeps = Seq(
  ExclusionRule(
    "org.scala-lang.modules",
    "scala-xml_2.13"
  )
)

val excludeAkkaXml = Seq(
  ExclusionRule(
    "com.typesafe.akka",
    "akka-http-xml_2.13"
  )
)

lazy val dockerSettings = Seq(
  Docker / maintainer      := "Platform Storage",
  dockerBaseImage          := sys.env.getOrElse("JDK_DOCKER_BASE_IMAGE", "openjdk:17-bullseye"),
  dockerPermissionStrategy := DockerPermissionStrategy.MultiStage
)

lazy val `migration-onboarding` = (project in file("."))
  .settings(buildSettings: _*)
  .aggregate()
