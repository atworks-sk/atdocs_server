package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
import com.sk.atdocs.dto.sample.SampleDto
import com.sk.atdocs.service.SampleService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/sample")
class SampleController (
    var sampleService: SampleService
    ) {

    @GetMapping("/select")
    fun sampleSelect(): ResponseEntity<List<SampleDto>> {
        logger.info { "request data : " + "/sample/select" }
        var res:List<SampleDto>? = sampleService.sampleSelect()

        return ResponseEntity.ok(res)
    }

    @GetMapping("/insert")
//    fun sampleInsert(@RequestParam name: String): ResponseEntity<Boolean> {
    fun sampleInsert(obj: SampleDto): ResponseEntity<Boolean> {
        logger.info { "request data : " + "/sample/insert" }
        logger.info { "name : " +  obj.name }

        return ResponseEntity.ok(sampleService.sampleInsert(obj.name))
    }


}