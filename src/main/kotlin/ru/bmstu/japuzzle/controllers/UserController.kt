package ru.bmstu.japuzzle.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    @RequestMapping("/register")
    fun register(
        @RequestParam("user") user: String
    ) {

    }
}