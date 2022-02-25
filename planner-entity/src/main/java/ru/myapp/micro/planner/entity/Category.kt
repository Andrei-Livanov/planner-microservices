package ru.myapp.micro.planner.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

/*
 Справочноное значение - категория пользователя.
 */

@Entity
@Table(name = "category", schema = "todo", catalog = "planner_todo")
class Category : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null

    @Column(name = "completed_count", updatable = false)
    var completedCount: Long? = null

    @Column(name = "uncompleted_count", updatable = false)
    var uncompletedCount: Long? = null

    @Column(name = "user_id")
    var userId: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val category = other as Category
        return id == category.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }

}
