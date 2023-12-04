package io.nopecho.sample.controller

import io.nopecho.sample.service.SampleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController(
    private val service: SampleService,
) {

    @GetMapping("/trace")
    fun trace(): ResponseEntity<Any> {
        val saved = service.save()

        return ResponseEntity.ok(saved)
    }

    @GetMapping("/trace2")
    fun trace2(): ResponseEntity<Any> {
        val entities = service.get()

        return ResponseEntity.ok(entities)
    }

    @GetMapping("/v1/trace/{id}")
    fun trace3(@PathVariable id: Long): ResponseEntity<Any> {
        val saved = service.getAndSave(id)

        return ResponseEntity.ok(saved)
    }

    @GetMapping("/v1/trace")
    fun trace4(): ResponseEntity<Any> {
        val saved = service.getAndSaveAll()

        return ResponseEntity.ok(saved)
    }

    @GetMapping("/v2/trace/{id}")
    fun trace5(@PathVariable id: Long): ResponseEntity<Any> {
        val saved = service.getAndSave2(id)

        return ResponseEntity.ok(saved)
    }

    @GetMapping("/v2/trace")
    fun trace6(): ResponseEntity<Any> {
        val saved = service.getAndSaveAll2()

        return ResponseEntity.ok(saved)
    }
}