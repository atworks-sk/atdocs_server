package com.sk.atdocs.controller
import com.sk.atdocs.dto.clazz.ClazzDeatailDto
import com.sk.atdocs.dto.method.MethodDetailDto
import com.sk.atdocs.dto.method.MethodDto
import com.sk.atdocs.dto.method.SearchListReqDto
import com.sk.atdocs.service.ClazzService
import com.sk.atdocs.service.MethodService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/method")
class MethodController (
    var methodService: MethodService
    ) {

    /*
     * method List search
     */
    @GetMapping("/searchList")
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): ResponseEntity<Page<MethodDto>>{
        return ResponseEntity.ok(methodService.searchList(reqDto, pageable))
    }

    /*
     * method Detail search
     */
    @GetMapping("searchDetail/{id}")
    fun searchDetail(@PathVariable id : Long) : ResponseEntity<MethodDetailDto>{
        return ResponseEntity.ok(methodService.searchDetail(id))
    }
}