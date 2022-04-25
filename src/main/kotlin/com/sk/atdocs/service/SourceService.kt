package com.sk.atdocs.service

import com.sk.atdocs.dto.source.AddSourceReqDto
import com.sk.atdocs.dto.source.SearchSourcesResDto
import com.sk.atdocs.dto.source.SearchSourcesTextResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SourceService {

    fun addSource(requestDto: AddSourceReqDto)
    fun searchSource(workId: Long, sourceName: String, sourceType: String, pageable: Pageable): Page<SearchSourcesResDto>?
    fun searchSourceText(workId: Long): SearchSourcesTextResDto?
    fun delete(sourceId: Long)
}