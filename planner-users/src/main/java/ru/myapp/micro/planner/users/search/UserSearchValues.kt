package ru.myapp.micro.planner.users.search

data class UserSearchValues(
    val email: String,
    val pageNumber: Int,
    val pageSize: Int,
    val sortColumn: String,
    val sortDirection: String
) {

    // остальные поля - необязательные к заполнению
    val username: String? = null
}
