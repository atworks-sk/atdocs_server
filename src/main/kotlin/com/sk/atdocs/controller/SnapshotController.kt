package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/snapshot")
class SnapshotController (
    var snapshotService: SnapshotService
    ) {

    @GetMapping()
    fun CreateSnapshot(@RequestParam path: String): ResponseEntity<Boolean> {
        logger.info { "request data : " + "/trans" }
        logger.info { "path : " + path }
        return ResponseEntity.ok(snapshotService.CreateSnapshot(path, 1L))
    }


}