package ru.myapp.micro.planner.entity

import java.util.*
import javax.persistence.*

/*
 Пользователь - основной объект.
 */

@Entity
@Table(name = "user_data", schema = "users", catalog = "planner_users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val email: String? = null

    val username: String? = null

    @Column(name = "userpassword")
    val password: String? = null

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @ManyToMany(mappedBy = "users")
//    private val roles: Set<Role>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as User
        return id == user.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return username!!
    }

}
