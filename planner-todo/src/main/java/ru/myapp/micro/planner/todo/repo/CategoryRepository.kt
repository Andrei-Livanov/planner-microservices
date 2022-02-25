package ru.myapp.micro.planner.todo.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.myapp.micro.planner.entity.Category

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    fun findByUserIdOrderByTitleAsc(id: Long): List<Category>

    @Query(
        "SELECT c FROM Category c WHERE " +
                "(:title IS NULL OR :title=''" +
                " OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title,'%')))" +
                " AND c.userId=:id" +
                " ORDER BY c.title ASC"
    )
    fun findByTitle(@Param("title") title: String?, @Param("id") id: Long): List<Category>

}
