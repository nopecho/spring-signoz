package io.nopecho.sample.service

import io.nopecho.sample.log
import io.nopecho.sample.repository.SampleEntity
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class SampleLogEventHandler {

    @Async("logEventThreadPool")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun transactionalEventListener(event: SampleEntity) {
        log().info("TransactionalEventListener | id: {}, name: {}", event.id, event.name)
    }

    @EventListener
    fun commonEventListener(event: SampleEntity) {
        log().info("CommonEventListener | id: {}, name: {}", event.id, event.name)
    }
}