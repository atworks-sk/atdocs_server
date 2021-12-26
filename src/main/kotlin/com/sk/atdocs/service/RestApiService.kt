package com.sk.atdocs.service

import com.sk.atdocs.dto.rest.RestApiListDto
import com.sk.atdocs.dto.rest.SearchListReqDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RestApiService {
    abstract fun searchList(reqDto: SearchListReqDto, pageable: Pageable) : Page<RestApiListDto>?
}