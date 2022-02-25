package ru.myapp.micro.planner.entity

import org.hibernate.annotations.Type
import java.io.Serializable
import java.util.*
import javax.persistence.*

/*
 Задачи пользователя.
 */

@Entity
@Table(name = "task", schema = "todo", catalog = "planner_todo")
class Task : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null

    @Type(type = "org.hibernate.type.NumericBooleanType") // для автоматической конвертации числа в true/false
    var completed: Boolean? = null

    @Column(name = "task_date")
    var taskDate: Date? = null

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    var priority: Priority? = null

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    var category: Category? = null

    @Column(name = "user_id")
    var userId: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val task = other as Task
        return id == task.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }

}
