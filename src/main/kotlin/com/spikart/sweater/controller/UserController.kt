package com.spikart.sweater.controller

import com.spikart.sweater.domain.Role
import com.spikart.sweater.domain.User
import com.spikart.sweater.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping
    fun userList(model: Model): String {
        val userList = userRepository.findAll()
        model.addAttribute("users", userList)
        return "userList"
    }

    @GetMapping("{user}")
    fun editUser(
        @PathVariable user: User,
        model: Model
    ): String {
        val u = user
        val r = user.roles
        model.addAttribute("user", user)
        model.addAttribute("roles", Role.values())
        return "userEdit"
    }

    @PostMapping
    fun saveUser(
        @RequestParam form: Map<String, String>,
        @RequestParam username: String,
        @RequestParam("userId") user: User,
        model: Model
    ): String {
        user.username = username
        user.roles.clear()

        val roles = Role.values().toMutableList()
        val newRoles = form.keys
        val selected = mutableSetOf<Role>()
        val ss = roles.fold(selected) { selectedRoles: MutableSet<Role>, role ->
            if (role.name in newRoles) selectedRoles.add(role)
            selectedRoles
        }
        user.roles = ss
        userRepository.save(user)
        return "redirect:/user"
    }

}