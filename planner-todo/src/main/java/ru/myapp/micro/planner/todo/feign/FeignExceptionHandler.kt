package ru.myapp.micro.planner.todo.feign

import feign.codec.ErrorDecoder
import org.springframework.web.server.ResponseStatusException
import com.google.common.io.CharStreams
import feign.Response
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.Reader
import java.nio.charset.Charset

@Component
class FeignExceptionHandler : ErrorDecoder {

    // вызывается каждый раз при ошибке вызова через Feign
    override fun decode(methodKey: String, response: Response): Exception? {
        when (response.status()) {
            406 -> {
                return ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, readMessage(response))
            }
        }
        return null
    }

    // получить текст ошибки в формате String из потока
    private fun readMessage(response: Response): String? {
        var message: String? = null
        var reader: Reader? = null
        try {
            reader = response.body().asReader(Charset.defaultCharset())
            message = CharStreams.toString(reader)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return message
    }

}
