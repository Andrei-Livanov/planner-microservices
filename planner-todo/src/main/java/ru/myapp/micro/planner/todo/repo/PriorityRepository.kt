package ru.myapp.micro.planner.todo.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.myapp.micro.planner.entity.Priority

@Repository
interface PriorityRepository : JpaRepository<Priority, Long> {

    fun findByUserIdOrderByIdAsc(id: Long): List<Priority>

    @Query(
        "SELECT p FROM Priority p WHERE " +
                "(:title IS NULL OR :title=''" +
                " OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title,'%')))" +
                " AND p.userId=:id" +
                " ORDER BY p.title ASC"
    )
    fun findByTitle(@Param("title") title: String?, @Param("id") id: Long): List<Priority>

}
