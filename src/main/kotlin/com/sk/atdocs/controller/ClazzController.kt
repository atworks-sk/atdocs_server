package com.sk.atdocs.controller
import com.sk.atdocs.dto.clazz.ClazzDeatailDto
import com.sk.atdocs.dto.clazz.ClazzListDto
import com.sk.atdocs.dto.clazz.SearchListReqDto
import com.sk.atdocs.service.ClazzService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/clazz")
class ClazzController (
    var clazzService: ClazzService
    ) {

    /*
     * clazz List search
     */
    @GetMapping("/searchList")
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): ResponseEntity<Page<ClazzListDto>>{
        return ResponseEntity.ok(clazzService.searchList(reqDto, pageable))
    }

    /*
     * clazz Detail search
     */
    @GetMapping("searchDetail/{id}")
    fun searchDetail(@PathVariable id : Long) : ResponseEntity<ClazzDeatailDto>{
        return ResponseEntity.ok(clazzService.searchDetail(id))
    }
}