package ru.myapp.micro.planner.entity

import java.util.*
import javax.persistence.*

/*
 Все доступные роли, которые будут привязаны к пользователю.
 */

@Entity
@Table(name = "role_data", schema = "users", catalog = "planner_users")
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val name: String? = null

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "user_role",
//        joinColumns = [JoinColumn(name = "role_id")],
//        inverseJoinColumns = [JoinColumn(name = "user_id")]
//    )
//    private val users: Set<User>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val role = other as Role
        return id == role.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return name!!
    }

}
