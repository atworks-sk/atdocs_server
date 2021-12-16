package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
//import com.sk.atdocs.dto.method.MethodListDto
import com.sk.atdocs.dto.snapshot.SearchListReqDto
import com.sk.atdocs.dto.snapshot.SnapshotDto
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    /*
    * method List search
    */
    @GetMapping("/searchList")
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): ResponseEntity<Page<SnapshotDto>>{
        return ResponseEntity.ok(snapshotService.searchList(reqDto, pageable))
    }

    @GetMapping()
    fun CreateSnapshot(@RequestParam path: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(snapshotService.CreateSnapshot(path, 1L))
    }


}