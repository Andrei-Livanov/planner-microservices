package ru.myapp.micro.planner.users.service

import org.springframework.data.domain.Page
import ru.myapp.micro.planner.users.repo.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.myapp.micro.planner.entity.User
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserService(private val repository: UserRepository) {

    fun add(user: User): User {
        return repository.save(user)
    }

    fun update(user: User): User {
        return repository.save(user)
    }

    fun deleteByUserId(id: Long) {
        repository.deleteById(id)
    }

    fun deleteByUserEmail(email: String) {
        repository.deleteByEmail(email)
    }

    fun findByEmail(email: String): User? {
        return repository.findByEmail(email)
    }

    fun findByParams(username: String?, email: String, paging: PageRequest): Page<User?> {
        return repository.findByParams(username, email, paging)
    }

    fun findById(id: Long): Optional<User> {
        return repository.findById(id)
    }

}
