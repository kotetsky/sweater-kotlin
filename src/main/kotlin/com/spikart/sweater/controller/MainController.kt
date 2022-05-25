package com.spikart.sweater.controller

import com.spikart.sweater.domain.Message
import com.spikart.sweater.domain.User
import com.spikart.sweater.repository.MessageRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Controller
class MainController(
    private val messageRepository: MessageRepository
) {

    @GetMapping("/main")
    fun main(
        model: MutableMap<String, Any>
    ): String {
        val messages: Iterable<Message> = messageRepository.findAll()
        model["messages"] = messages
        return "main"
    }

    @GetMapping("/")
    fun greeting(
        model: MutableMap<String, Any>
    ): String {
        return "greeting"
    }

    @PostMapping("/main")
    fun addMessage(
        @AuthenticationPrincipal user: User,
        @RequestParam text: String,
        @RequestParam tag: String,
        model: MutableMap<String, Any>,

        ): String {
        val message = Message(text = text, tag = tag, author = user)
        messageRepository.save(message)
        val messages: Iterable<Message> = messageRepository.findAll()

        model["messages"] = messages
        return "main"
    }

    @PostMapping("/filter")
    fun filterMessage(
        @RequestParam tag: String?,
        model: MutableMap<String, Any>,
    ): String {
        val messages = if (tag.isNullOrBlank()) {
            messageRepository.findAll()
        } else {
            messageRepository.findByTag(tag)
        }
        model["messages"] = messages

        return "/main"
    }

}