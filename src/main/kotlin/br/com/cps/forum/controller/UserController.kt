package br.com.cps.forum.controller

import br.com.cps.forum.dto.UserEmailForm
import br.com.cps.forum.dto.UserToBlockForm
import br.com.cps.forum.dto.UserToUnblockForm
import br.com.cps.forum.dto.UserView
import br.com.cps.forum.model.User
import br.com.cps.forum.repository.UserRepository
import br.com.cps.forum.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val userRepository: UserRepository
) {
    @GetMapping("/all-users")
    fun getAllUsersActives(): List<UserView> = userService.getAllUsers()

    @GetMapping("/by-email")
    fun resultAllDataUser(@RequestBody @Valid email: UserEmailForm) = userService.getUserByEmail(email)

    @PostMapping("/create")
    fun createUser() = userService.createUser()

    @PutMapping("/block-user")
    fun blockUserByAdmin(@RequestBody @Valid userToBlock: UserToBlockForm): String = userService.blockUser(userToBlock)

    @PutMapping("/unblock-user")
    fun unblockedUserByAdmin(@RequestBody @Valid userUnblock: UserToUnblockForm): String =
        userService.unblockUser(userUnblock)

    @DeleteMapping("/delete-by-id/{id}")
    fun deleteUser(@PathVariable id: Long) = userRepository.deleteById(id)
}