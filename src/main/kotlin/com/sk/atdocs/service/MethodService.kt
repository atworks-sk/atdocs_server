package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.MethodArgsEntity
import com.sk.atdocs.dto.method.MethodDto
import com.sk.atdocs.dto.method.SearchListReqDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MethodService {
    // SanpshotService에서 호출하는 Metho 생성 메서드
//    fun createMethod(methodEntity: MethodEntity) : MethodEntity

//    fun createMethodParam(methodArgsEntity: MethodArgsEntity): MethodArgsEntity

    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<MethodDto>?


}