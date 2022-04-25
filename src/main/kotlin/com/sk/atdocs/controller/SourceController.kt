package com.sk.atdocs.controller
//import lombok.RequiredArgsConstructor
import com.sk.atdocs.dto.source.AddSourceReqDto
import com.sk.atdocs.dto.source.SearchSourcesResDto
import com.sk.atdocs.dto.source.SearchSourcesTextResDto
import com.sk.atdocs.dto.work.SearchWorksResDto
import com.sk.atdocs.service.SourceService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/source")
class SourceController (
    var sourceService: SourceService
    ) {

    @GetMapping("/work-id/{workId}")
    fun searchSource(@PathVariable workId : Long, @RequestParam sourceName : String, @RequestParam sourceType : String, pageable: Pageable): ResponseEntity<Page<SearchSourcesResDto>> {
        return ResponseEntity.ok(sourceService.searchSource(workId, sourceName, sourceType, pageable))
    }

    @GetMapping("/work-id-text/{workId}")
    fun searchSourceText(@PathVariable workId : Long): ResponseEntity<SearchSourcesTextResDto> {
        return ResponseEntity.ok(sourceService.searchSourceText(workId))
    }

    @PostMapping("")
    fun addSource(@RequestBody requestDto : AddSourceReqDto): ResponseEntity<HttpStatus>{
        sourceService.addSource(requestDto)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{sourceId}")
    fun delete(@PathVariable sourceId : Long) : ResponseEntity<HttpStatus>{
        sourceService.delete(sourceId)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }


}