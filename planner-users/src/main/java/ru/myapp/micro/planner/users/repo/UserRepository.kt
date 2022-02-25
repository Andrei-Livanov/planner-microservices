package ru.myapp.micro.planner.users.repo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.myapp.micro.planner.entity.User

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun deleteByEmail(email: String)

    fun findByEmail(email: String): User?

    @Query(
        "SELECT u FROM User u WHERE " +
                "(:email IS NULL OR :email='' OR LOWER(u.email) LIKE  LOWER(CONCAT('%', :email,'%') ) ) AND " +
                "(:username IS NULL OR :username='' OR LOWER(u.username) LIKE  LOWER(CONCAT('%', :username,'%')))"
    )
    fun findByParams(
        @Param("username") username: String?,
        @Param("email") email: String,
        pageable: Pageable
    ): Page<User?>

}
