package dk.edutor

import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong


data class Greeting (val id: Long, val content: String)

@RestController
class GreetingController {
  val counter = AtomicLong()

  @RequestMapping("/hello")
  fun hello(@RequestParam(value = "name", defaultValue = "World") name: String) =
    Greeting(counter.incrementAndGet(), "Hello $name you are #$counter")

  }