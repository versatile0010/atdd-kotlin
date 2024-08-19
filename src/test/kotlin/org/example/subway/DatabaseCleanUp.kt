package org.example.subway

import com.google.common.base.CaseFormat
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Component
@ActiveProfiles("test")
class DatabaseCleanUp : InitializingBean {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private lateinit var tableNames: List<String>

    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities
            .filter { entity -> entity.javaType.getAnnotation(Entity::class.java) != null }
            .map { entity -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entity.name) }
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()

        for (tableName in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $tableName ALTER COLUMN ID RESTART WITH 1").executeUpdate()
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}