package ru.myapp.micro.planner.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

/*
  Вся активность пользователя (активация аккаунта, другие действия по необходимости).
 */

@Entity
@Table(name = "activity", schema = "todo", catalog = "planner_todo")
class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Type(type = "org.hibernate.type.NumericBooleanType")
    val activated: Boolean? = null // становится true только после подтверждения активации пользователем

    @Column(updatable = false)
    val uuid: String? = null // создается только один раз с помощью триггера в БД

    @Column(name = "user_id")
    val userId: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val activity = other as Activity
        return id == activity.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

}
