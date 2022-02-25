package ru.myapp.micro.planner.todo.repo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.myapp.micro.planner.entity.Task
import java.util.*

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findByUserIdOrderByTitleAsc(userId: Long): List<Task>

    @Query(
        "SELECT t FROM Task t WHERE " +
                "(:title IS NULL OR :title='' OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title,'%'))) AND " +
                "(:completed IS NULL OR t.completed=:completed) AND " +
                "(:priorityId IS NULL OR t.priority.id=:priorityId) AND " +
                "(:categoryId IS NULL OR t.category.id=:categoryId) AND " +
                "(:categoryId IS NULL OR t.category.id=:categoryId) AND " +
                "(" +
                "(cast(:dateFrom AS timestamp) IS NULL OR t.taskDate>=:dateFrom) AND " +
                "(cast(:dateTo AS timestamp) IS NULL OR t.taskDate<=:dateTo)" +
                ") AND " +
                "(t.userId=:userId)"
    )
    fun findByParams(
        @Param("title") title: String?,
        @Param("completed") completed: Boolean?,
        @Param("priorityId") priorityId: Long?,
        @Param("categoryId") categoryId: Long?,
        @Param("userId") userId: Long,
        @Param("dateFrom") dateFrom: Date?,
        @Param("dateTo") dateTo: Date?,
        pageable: Pageable
    ): Page<Task>

}
