package ru.myapp.micro.planner.todo.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.myapp.micro.planner.entity.Category
import ru.myapp.micro.planner.todo.feign.UserFeignClient
import ru.myapp.micro.planner.todo.search.CategorySearchValues
import ru.myapp.micro.planner.todo.service.CategoryService

@RestController
@RequestMapping("/category")
class CategoryController(
    private val categoryService: CategoryService,
    @Qualifier("ru.myapp.micro.planner.todo.feign.UserFeignClient") private val userFeignClient: UserFeignClient
) {

    @PostMapping("/add")
    fun add(@RequestBody category: Category): ResponseEntity<Any> {

        if (category.id != null && category.id != 0L) {
            return ResponseEntity<Any>("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE)
        }

        if (category.title == null || category.title!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title must not be null", HttpStatus.NOT_ACCEPTABLE)
        }

        if (category.userId == null || category.userId == 0L) {
            return ResponseEntity<Any>("missed param: userId must not be null", HttpStatus.NOT_ACCEPTABLE)
        }

//        if (userWebClientBuilder.userExists(category.getUserId())) {
//            return ResponseEntity.ok(categoryService.add(category));
//        }

//        userWebClientBuilder.userExistsAsync(category.getUserId()).subscribe(user -> System.out.println("user = " + user));

        val response = userFeignClient.findUserById(category.userId!!)
            ?: return ResponseEntity<Any>("user system unavailable, please try again later", HttpStatus.NOT_FOUND)

        return if (response.body != null) {
            ResponseEntity.ok(categoryService.add(category))
        } else {
            ResponseEntity<Any>("user id=" + category.userId + " not found", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @PutMapping("/update")
    fun update(@RequestBody category: Category): ResponseEntity<Any> {

        if (category.id == null || category.id == 0L) {
            return ResponseEntity<Any>("missed param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        if (category.title == null || category.title!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        categoryService.update(category)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {

        try {
            categoryService.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/all")
    fun findAll(@RequestBody userId: Long): List<Category> {
        return categoryService.findAll(userId)
    }

    @PostMapping("/search")
    fun search(@RequestBody categorySearchValues: CategorySearchValues): ResponseEntity<Any> {

        if (categorySearchValues.userId == 0L) {
            return ResponseEntity<Any>("missed param: user id", HttpStatus.NOT_ACCEPTABLE)
        }

        val list = categoryService.findByTitle(categorySearchValues.title, categorySearchValues.userId)

        return ResponseEntity.ok(list)
    }

    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {

        val category: Category? = try {
            categoryService.findById(id)
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity.ok(category)
    }

}
