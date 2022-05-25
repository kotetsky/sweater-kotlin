package com.spikart.sweater.controller

import com.spikart.sweater.domain.Role
import com.spikart.sweater.domain.User
import com.spikart.sweater.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class RegistrationController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/registration")
    fun registration(): String {
        return "registration"
    }

    @PostMapping("/registration")
    fun registerUser(
        user: User,
        model: MutableMap<String, Any>
    ): String {
        val dbUser: User? = userRepository.findByUsername(user.username)
        dbUser?.apply {
            model["message"] = "user exists"
            return "registration"

        }

        user.isActive = true
        user.roles = mutableSetOf(Role.USER)
        userRepository.save(user)
        return "redirect:/login"
    }

}