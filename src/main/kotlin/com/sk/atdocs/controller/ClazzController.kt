package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
import com.sk.atdocs.dto.SaveResDto
import com.sk.atdocs.dto.project.ProjectDto
import com.sk.atdocs.dto.project.SearchListReqDto
import com.sk.atdocs.service.ClazzService
import com.sk.atdocs.service.ProjectService
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/clazz")
class ClazzController (
    var clazzService: ClazzService
    ) {

    /*
     * project List search
     */
    @GetMapping("/searchList")
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): ResponseEntity<HttpStatus>{
//        return ResponseEntity.ok(projectService.searchList(reqDto, pageable))
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

//    @PutMapping("/save")
//    fun save(@RequestBody reqDto : ProjectDto) : ResponseEntity<HttpStatus>{
//        projectService.save(reqDto)
//        return ResponseEntity<HttpStatus>(HttpStatus.OK)
//    }
//
//    @DeleteMapping("delete/{id}")
//    fun delete(@PathVariable id : Long) : ResponseEntity<HttpStatus>{
//        logger.info { "id -> " + id }
//        projectService.delete(id)
//        return ResponseEntity<HttpStatus>(HttpStatus.OK)
//    }

}