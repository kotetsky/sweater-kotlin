package com.spikart.sweater.controller

import com.spikart.sweater.domain.Message
import com.spikart.sweater.domain.User
import com.spikart.sweater.repository.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.*

const val UPLOAD_PATH = "upload.path"

@Controller
class MainController {

    @Autowired
    private lateinit var messageRepository: MessageRepository

    @Value("\${upload.path}")
    private lateinit var uploadPath: String

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
    @Throws(IOException::class)
    fun addMessage(
        @AuthenticationPrincipal user: User,
        @RequestParam text: String,
        @RequestParam tag: String,
        @RequestParam file: MultipartFile?,
        model: Model
    ): String {

        var message = Message(
            text = text,
            tag = tag,
            author = user
        )

        file?.apply {
            val uploadDir = File(uploadPath)
            if (!uploadDir.exists()) {
                uploadDir.mkdirs()
            }

            val uuid = UUID.randomUUID().toString()
            val fileName = "$uuid.${file.originalFilename}"

            file.transferTo(File("$uploadPath/$fileName"))
            message = message.copy(
                filename = fileName
            )
        }

        messageRepository.save(message)
        val messages: Iterable<Message> = messageRepository.findAll()

        model.addAttribute("messages", messages)
        model.addAttribute("filter", "")
        return "redirect:main"
    }
}