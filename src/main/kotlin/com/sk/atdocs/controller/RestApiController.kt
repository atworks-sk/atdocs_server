package com.sk.atdocs.controller
import com.sk.atdocs.dto.rest.RestApiListDto
import com.sk.atdocs.dto.rest.SearchListReqDto
import com.sk.atdocs.service.RestApiService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {  }

@RestController
@RequestMapping("/api/v1/rest")
class RestApiController (
    var restApiService: RestApiService
    ) {

    /*
     * method List search
     */
    @GetMapping("/searchList")
//    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): ResponseEntity<Page<RestListDto>>{
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): ResponseEntity<Page<RestApiListDto>>{
//        methodService.searchList(reqDto, pageable)


        logger.info { reqDto.toString() }
        //    return ResponseEntity.ok(methodService.searchList(reqDto, pageable))
        return ResponseEntity.ok(restApiService.searchList(reqDto, pageable))
    }

}