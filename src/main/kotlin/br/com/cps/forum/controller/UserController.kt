package br.com.cps.forum.controller

import br.com.cps.forum.dto.UserEmailForm
import br.com.cps.forum.dto.UserToBlockForm
import br.com.cps.forum.dto.UserToUnblockForm
import br.com.cps.forum.dto.UserView
import br.com.cps.forum.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/user-by-email")
    fun resultAllDataUser(@RequestBody @Valid email: UserEmailForm): UserView? = userService.getUserByEmail(email)

    @PutMapping("/block-user")
    fun blockUserByAdmin(@RequestBody @Valid userToBlock: UserToBlockForm): String = userService.blockUser(userToBlock)

    @PutMapping("/unblock-user")
    fun unblockedUserByAdmin(@RequestBody @Valid userUnblock: UserToUnblockForm): String =
        userService.unblockUser(userUnblock)
}