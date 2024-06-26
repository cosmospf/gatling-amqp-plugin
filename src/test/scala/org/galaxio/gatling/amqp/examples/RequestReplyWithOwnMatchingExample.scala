package org.galaxio.gatling.amqp.examples

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import org.galaxio.gatling.amqp.Predef._
import Utils.idFeeder
import org.galaxio.gatling.amqp.protocol.AmqpProtocolBuilder
import org.galaxio.gatling.amqp.request.AmqpProtocolMessage

import scala.concurrent.duration._

class RequestReplyWithOwnMatchingExample extends Simulation {

  def matchByMessage(message: AmqpProtocolMessage): String = {
    // do something with the message and extract the values you are interested in
    // method is called:
    // - for each message which will be sent out
    // - for each message which has been received
    "1" // just returning something,
  }

  val amqpConf: AmqpProtocolBuilder = amqp
    .connectionFactory(
      rabbitmq
        .host("localhost")
        .port(5672)
        .username("guest")
        .password("guest")
        .vhost("/"),
    )
    .replyTimeout(60000)
    .consumerThreadsCount(8)
    .matchByMessage(matchByMessage)
    .usePersistentDeliveryMode

  val scn: ScenarioBuilder = scenario("AMQP test")
    .feed(idFeeder)
    .exec(
      amqp("Request Reply exchange test").requestReply
        .topicExchange("test_queue_in", "we")
        .replyExchange("test_queue_out")
        .textMessage("""{"msg": "Hello message - #{id}"}""")
        .messageId("#{id}")
        .priority(0)
        .contentType("application/json")
        .headers("test" -> "performance", "extra-test" -> "34-#{id}")
        .check(
          bodyString.exists,
          bodyString.is("Message processed"),
        ),
    )

  setUp(
    scn.inject(rampUsersPerSec(1) to 5 during (60 seconds), constantUsersPerSec(5) during (2 minutes)),
  ).protocols(amqpConf)
    .maxDuration(10 minutes)
}
