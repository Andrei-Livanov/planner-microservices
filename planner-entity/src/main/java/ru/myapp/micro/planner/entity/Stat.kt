package ru.myapp.micro.planner.entity

import java.util.*
import javax.persistence.*

/*
 Общая статистика по задачам (незвисимо от категорий задач).
 */

@Entity
@Table(name = "stat", schema = "todo", catalog = "planner_todo")
class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "completed_total", updatable = false)
    private val completedTotal: Long? = null // значение задается в триггере в БД

    @Column(name = "uncompleted_total", updatable = false)
    private val uncompletedTotal: Long? = null // значение задается в триггере в БД

    @Column(name = "user_id")
    private val userId: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val stat = other as Stat
        return id == stat.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

}
