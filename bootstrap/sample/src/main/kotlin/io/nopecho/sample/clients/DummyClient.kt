package io.nopecho.sample.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(name = "dummyClient", url = "https://dummy.restapiexample.com")
interface DummyClient {
    @GetMapping("/api/v1/employees")
    fun getEmployees(): Map<*, *>

    @GetMapping("/api/v1/employee/{id}")
    fun getEmployee(@PathVariable id: String): Map<*, *>
}