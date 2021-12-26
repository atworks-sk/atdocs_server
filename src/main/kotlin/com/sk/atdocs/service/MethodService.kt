package com.sk.atdocs.service

import com.sk.atdocs.dto.method.MethodDto
import com.sk.atdocs.dto.method.MethodListDto
import com.sk.atdocs.dto.method.SearchListReqDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MethodService {

    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<MethodListDto>?

    fun searchDetail(id: Long): MethodDto?
}