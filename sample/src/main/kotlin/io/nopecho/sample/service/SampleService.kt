package io.nopecho.sample.service

import io.nopecho.sample.clients.DummyClient
import io.nopecho.sample.clients.DummyClient2
import io.nopecho.sample.repository.SampleEntity
import io.nopecho.sample.repository.SampleRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SampleService(
    private val repository: SampleRepository,
    private val dummyClient: DummyClient,
    private val dummyClient2: DummyClient2,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun save(): SampleEntity = runBlocking {
        val date = LocalDateTime.now().toString()
        val entity = SampleEntity(
            name = "sample!!-$date",
            description = date
        )
        val saved = repository.save(entity)
        launch { eventPublisher.publishEvent(saved) }
        saved
    }

    @Transactional(readOnly = true)
    fun get(): List<SampleEntity> = runBlocking {
        val entities = repository.findAll()
        entities.forEach { launch { eventPublisher.publishEvent(it) } }
        entities
    }

    @Transactional
    fun getAndSave(id: Long) = runBlocking {
        val employee = dummyClient.getEmployee(id.toString())
        val employeeMap = employee["data"] as Map<*, *>
        val entity = SampleEntity(
            name = employeeMap["employee_name"].toString(),
            description = "age..: ${employeeMap["employee_age"].toString()}"
        )
        val saved = repository.save(entity)
        launch { eventPublisher.publishEvent(saved) }
        saved
    }

    @Transactional
    fun getAndSaveAll(): List<SampleEntity> = runBlocking {
        val employees = dummyClient.getEmployees()
        val employeeMap = employees["data"] as List<Map<*, *>>

        val entities = employeeMap.map {
            SampleEntity(
                name = it["employee_name"].toString(),
                description = "age..: ${it["employee_age"].toString()}"
            )
        }
        val saved = repository.saveAll(entities)
        saved.forEach { launch { eventPublisher.publishEvent(it) } }
        saved
    }

    @Transactional
    fun getAndSave2(id: Long) = runBlocking {
        val product = dummyClient2.getProduct(id.toString())
        val entity = SampleEntity(
            name = product["title"].toString(),
            description = product["description"].toString()
        )
        val saved = repository.save(entity)
        launch { eventPublisher.publishEvent(saved) }
        saved
    }

    @Transactional
    fun getAndSaveAll2() = runBlocking {
        val products = dummyClient2.getProducts()

        val productList = products["products"] as List<Map<*, *>>
        val entities = productList.map {
            SampleEntity(
                name = it["title"].toString(),
                description = it["description"].toString()
            )
        }
        val saved = repository.saveAll(entities)
        saved.forEach { launch { eventPublisher.publishEvent(it) } }
        saved
    }
}
