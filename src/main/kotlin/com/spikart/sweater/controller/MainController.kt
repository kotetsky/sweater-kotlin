package com.spikart.sweater.controller

import com.spikart.sweater.domain.Message
import com.spikart.sweater.domain.User
import com.spikart.sweater.repository.MessageRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MainController(
    private val messageRepository: MessageRepository
) {

    @GetMapping("/main")
    fun main(
        @RequestParam(required = false, defaultValue = "") filter: String,
        model: Model
    ): String {
        val messages = if (filter.isEmpty()) {
            messageRepository.findAll()
        } else {
            messageRepository.findByTag(filter)
        }
        model.addAttribute("messages", messages)
        model.addAttribute("filter", filter)
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
        model: Model
    ): String {
        val message = Message(text = text, tag = tag, author = user)
        messageRepository.save(message)
        val messages: Iterable<Message> = messageRepository.findAll()

        model.addAttribute("messages", messages)
        model.addAttribute("filter", "")
        return "main"
    }
}