package ru.myapp.micro.planner.todo.service

import org.springframework.stereotype.Service
import ru.myapp.micro.planner.entity.Priority
import ru.myapp.micro.planner.todo.repo.PriorityRepository
import javax.transaction.Transactional

@Service
@Transactional
class PriorityService(
    private val repository: PriorityRepository
) {

    fun add(priority: Priority): Priority {
        return repository.save(priority)
    }

    fun update(priority: Priority): Priority {
        return repository.save(priority)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    fun findAll(userId: Long): List<Priority> {
        return repository.findByUserIdOrderByIdAsc(userId)
    }

    fun findByTitle(title: String?, userId: Long): List<Priority> {
        return repository.findByTitle(title, userId)
    }

    fun findById(id: Long): Priority {
        return repository.findById(id).get()
    }

}
