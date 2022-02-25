package ru.myapp.micro.planner.todo.service

import org.springframework.stereotype.Service
import ru.myapp.micro.planner.entity.Category
import ru.myapp.micro.planner.todo.repo.CategoryRepository
import javax.transaction.Transactional

@Service
@Transactional
class CategoryService(
    private val repository: CategoryRepository
) {

    fun add(category: Category): Category {
        return repository.save(category)
    }

    fun update(category: Category): Category {
        return repository.save(category)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    fun findAll(userId: Long): List<Category> {
        return repository.findByUserIdOrderByTitleAsc(userId)
    }

    fun findByTitle(text: String?, userId: Long): List<Category> {
        return repository.findByTitle(text, userId)
    }

    fun findById(id: Long): Category {
        return repository.findById(id).get()
    }

}
