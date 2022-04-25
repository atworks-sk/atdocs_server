package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
import com.sk.atdocs.dto.project.ProjectDto
import com.sk.atdocs.dto.source.AddSourceReqDto
import com.sk.atdocs.service.ProjectService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/project")
class ProjectController (
    var projectService: ProjectService
    ) {

    @GetMapping("/project-name-like")
    fun searchList(@RequestParam projectName : String, pageable: Pageable): ResponseEntity<Page<ProjectDto>> {
        return ResponseEntity.ok(projectService.searchList(projectName, pageable))
    }

    @GetMapping("/project-id/{projectId}")
    fun searchDetail(@PathVariable projectId : Long): ResponseEntity<ProjectDto> {
        return ResponseEntity.ok(projectService.searchProjectDetail(projectId))
    }


    @PutMapping("")
    fun save(@RequestBody reqDto : ProjectDto) : ResponseEntity<HttpStatus>{
        projectService.save(reqDto)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id : Long) : ResponseEntity<HttpStatus>{
        projectService.delete(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    /*
     * project List search
     */
    @GetMapping("/searchListWithoutPage")
    fun searchListWithoutPage(): ResponseEntity<ArrayList<ProjectDto>> {
        return ResponseEntity.ok(projectService.searchListWithoutPage())
    }

    @PostMapping("/analysis/{projectId}")
    fun addSource(@PathVariable projectId : Long): ResponseEntity<HttpStatus>{

        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

}