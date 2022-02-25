package ru.myapp.micro.planner.todo.service

import org.springframework.stereotype.Service
import ru.myapp.micro.planner.entity.Category
import ru.myapp.micro.planner.entity.Priority
import ru.myapp.micro.planner.entity.Task
import java.util.*

@Service
class TestDataService(
    private val taskService: TaskService,
    private val priorityService: PriorityService,
    private val categoryService: CategoryService
) {

    fun initTestData(userId: Long) {

        val priority1 = Priority()
        priority1.color = "#fff"
        priority1.title = "Важный"
        priority1.userId = userId

        val priority2 = Priority()
        priority2.color = "#ffe"
        priority2.title = "Неважный"
        priority2.userId = userId

        priorityService.add(priority1)
        priorityService.add(priority2)

        val category1 = Category()
        category1.title = "Работа"
        category1.userId = userId

        val category2 = Category()
        category2.title = "Семья"
        category2.userId = userId

        categoryService.add(category1)
        categoryService.add(category2)

        // завтра
        var tomorrow = Date()
        val calendar1 = Calendar.getInstance()
        calendar1.time = tomorrow
        calendar1.add(Calendar.DATE, 1)
        tomorrow = calendar1.time

        // неделя
        var oneWeek = Date()
        val calendar2 = Calendar.getInstance()
        calendar2.time = oneWeek
        calendar2.add(Calendar.DATE, 7)
        oneWeek = calendar2.time

        val task1 = Task()
        task1.title = "Покушать"
        task1.category = category1
        task1.priority = priority1
        task1.completed = true
        task1.taskDate = tomorrow
        task1.userId = userId

        val task2 = Task()
        task2.title = "Поспать"
        task2.category = category2
        task2.priority = priority2
        task2.completed = false
        task2.taskDate = oneWeek
        task2.userId = userId

        taskService.add(task1)
        taskService.add(task2)
    }

}
