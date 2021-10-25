package ru.bmstu.japuzzle.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.bmstu.japuzzle.entities.UserEntity
import ru.bmstu.japuzzle.repositories.UserRepository

@CrossOrigin
@RestController
@RequestMapping("/user")
class UserController @Autowired constructor (
    val userRepository: UserRepository
) {
    @RequestMapping("/register")
    fun register(
        @RequestParam("user") user: String
    ): ResponseEntity<String> {
        if (userRepository.existsByName(user)) {
            return ResponseEntity("User:$user is already registered", HttpStatus.FOUND);
        }
        val u = userRepository.save(UserEntity(user))
        return ResponseEntity("User:${u.name} registered", HttpStatus.CREATED)
    }
}