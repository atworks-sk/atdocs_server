package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
//import com.sk.atdocs.dto.method.MethodListDto
import com.sk.atdocs.dto.snapshot.CreateReqDto
import com.sk.atdocs.dto.snapshot.SearchListReqDto
import com.sk.atdocs.dto.snapshot.SnapshotDto
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/create")
    fun CreateSnapshot(@RequestBody reqDto: CreateReqDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok(snapshotService.CreateSnapshot(reqDto.dirPath!!, reqDto.projectId!!))
    }


    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id : Long) : ResponseEntity<HttpStatus>{
        snapshotService.deleteSnapshot(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

}