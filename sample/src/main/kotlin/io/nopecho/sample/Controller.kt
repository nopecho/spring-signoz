package io.nopecho.sample

import io.micrometer.observation.annotation.Observed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @Observed
    @GetMapping("/trace")
    fun trace(): ResponseEntity<Any> {
        log().info("trace logger!")
        return ResponseEntity.ok("ok")
    }
}