package com.spikart.sweater.controller

import com.spikart.sweater.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping
    fun getUser(model: Model): String {
        model.addAttribute("users", userRepository.findAll())
        return "userList"
    }

}