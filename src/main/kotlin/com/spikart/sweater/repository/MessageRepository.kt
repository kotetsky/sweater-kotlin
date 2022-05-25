package com.spikart.sweater.repository

import com.spikart.sweater.domain.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: CrudRepository<Message, Long> {

    fun findByTag(tag: String): List<Message>

}