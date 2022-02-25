package ru.myapp.micro.planner.todo.service

import org.springframework.data.domain.Page
import ru.myapp.micro.planner.todo.repo.TaskRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.myapp.micro.planner.entity.Task
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class TaskService(
    private val repository: TaskRepository
) {

    fun add(task: Task): Task {
        return repository.save(task)
    }

    fun update(task: Task): Task {
        return repository.save(task)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    fun findAll(userId: Long): List<Task> {
        return repository.findByUserIdOrderByTitleAsc(userId)
    }

    fun findByParams(
        text: String?,
        completed: Boolean?,
        priorityId: Long?,
        categoryId: Long?,
        userId: Long,
        dateFrom: Date?,
        dateTo: Date?,
        paging: PageRequest
    ): Page<Task> {
        return repository.findByParams(text, completed, priorityId, categoryId, userId, dateFrom, dateTo, paging)
    }

    fun findById(id: Long): Task {
        return repository.findById(id).get()
    }

}
