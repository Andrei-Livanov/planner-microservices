package ru.myapp.micro.planner.todo.service

import org.springframework.stereotype.Service
import ru.myapp.micro.planner.todo.repo.StatRepository
import ru.myapp.micro.planner.entity.Stat
import javax.transaction.Transactional

@Service
@Transactional
class StatService(
    private val repository: StatRepository
) {

    fun findStat(userId: Long): Stat? {
        return repository.findByUserId(userId)
    }

}
