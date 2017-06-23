package dk.edutor

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.socket.config.annotation.*
import java.util.concurrent.atomic.AtomicLong


data class Greeting (val id: Long, val content: String)

class Message() {
  var name: String = "Killroy"
  }

@RestController
class GreetingController {
  val counter = AtomicLong()

  @RequestMapping("/hello")
  fun hello(@RequestParam(value = "name", defaultValue = "World") name: String) =
    Greeting(counter.incrementAndGet(), "Hello $name you are #$counter")

  }

@Controller
class MessageController {
  @MessageMapping("/hi")
  @SendTo("/topic/greetings")
  fun greeting(message: Message): Greeting {
    Thread.sleep(1000L)
    return Greeting(4711, "Hi ${message.name}!")
    }
  }

@Configuration
@EnableWebSocketMessageBroker
open class WebSocketConfig : AbstractWebSocketMessageBrokerConfigurer() {

  override fun configureMessageBroker(registry: MessageBrokerRegistry) {
    registry.enableSimpleBroker("/topic")
    registry.setApplicationDestinationPrefixes("/app")
    }

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    registry.addEndpoint("/gs-guide-websocket").withSockJS()
    }

  }

