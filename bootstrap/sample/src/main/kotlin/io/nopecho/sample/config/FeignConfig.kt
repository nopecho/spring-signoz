package io.nopecho.sample.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["io.nopecho.sample"])
@Configuration
class FeignConfig