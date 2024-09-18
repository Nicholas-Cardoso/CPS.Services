package br.com.cps.forum.controller

import br.com.cps.forum.dto.UserEmailForm
import br.com.cps.forum.model.User
import br.com.cps.forum.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-result")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun resultAllDataUser(@RequestBody @Valid email: UserEmailForm): User? = userService.getUserByEmail(email)

//    @POST("/block-user")
//    fun blockUserByAdmin(@RequestBody @Valid userToBlock: UserToBlockForm) {
//
//    }
}