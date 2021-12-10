package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
import com.sk.atdocs.dto.SaveResDto
import com.sk.atdocs.dto.project.ProjectDto
import com.sk.atdocs.dto.project.SearchListReqDto
import com.sk.atdocs.service.ProjectService
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/project")
class ProjectController (
    var projectService: ProjectService
    ) {

    /*
     * project List search
     */
    @GetMapping("/searchList")
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): ResponseEntity<Page<ProjectDto>> {
        return ResponseEntity.ok(projectService.searchList(reqDto, pageable))
    }

    @PostMapping("/save")
    fun save(@RequestBody reqDto : ProjectDto) : ResponseEntity<SaveResDto>{
        return ResponseEntity.ok(projectService.save(reqDto))
    }


}