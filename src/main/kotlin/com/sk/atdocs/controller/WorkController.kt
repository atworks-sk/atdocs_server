package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
import com.sk.atdocs.dto.source.AddSourceReqDto
import com.sk.atdocs.dto.work.SaveWorkReqDto
import com.sk.atdocs.dto.work.SearchWorksResDto
import com.sk.atdocs.service.WorkService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/work")
class WorkController (
    var workService: WorkService
    ) {

    @PutMapping("")
    fun save(@RequestBody reqDto : SaveWorkReqDto) : ResponseEntity<HttpStatus>{
        workService.save(reqDto)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @GetMapping("/project-id/{projectId}")
    fun searchWorks(@PathVariable projectId : Long, @RequestParam workName : String, pageable: Pageable): ResponseEntity<Page<SearchWorksResDto>> {
        return ResponseEntity.ok(workService.searchWorks(projectId, workName, pageable))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : Long) : ResponseEntity<HttpStatus>{
        workService.delete(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }


}