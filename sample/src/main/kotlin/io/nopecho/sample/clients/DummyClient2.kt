package io.nopecho.sample.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "dummyClient2", url = "https://dummyjson.com")
interface DummyClient2 {

    @GetMapping("/products")
    fun getProducts(): Map<*, *>

    @GetMapping("/products/{id}")
    fun getProduct(@PathVariable id: String): Map<*, *>
}