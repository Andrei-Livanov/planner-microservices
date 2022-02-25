package ru.myapp.micro.planner.todo.search

import java.util.*

// дата классы для поиска

data class TaskSearchValues(
    var sortColumn: String,
    var sortDirection: String,
    var pageNumber: Int,
    var pageSize: Int,
    var userId: Long
) {

    // остальные поля - необязательные к заполнению
    var title: String? = null
    var completed: Int? = null
    var priorityId: Long? = null
    var categoryId: Long? = null

    // для задания периода по датам
    var dateFrom: Date? = null
    var dateTo: Date? = null
}

data class PrioritySearchValues(var userId: Long) {
    var title: String? = null
}

data class CategorySearchValues(var userId: Long) {
    var title: String? = null
}
