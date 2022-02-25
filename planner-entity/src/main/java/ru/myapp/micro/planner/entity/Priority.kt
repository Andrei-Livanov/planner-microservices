package ru.myapp.micro.planner.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

/*
 Справочное значение - приоритет пользователя.
 */

@Entity
@Table(name = "priority", schema = "todo", catalog = "planner_todo")
class Priority : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null

    var color: String? = null

    @Column(name = "user_id")
    var userId: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val priority = other as Priority
        return id == priority.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }

}
