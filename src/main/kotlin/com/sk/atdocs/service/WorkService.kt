package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.WorkEntity
import com.sk.atdocs.dto.work.SaveWorkReqDto
import com.sk.atdocs.dto.work.SearchWorksResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface WorkService {

    fun save(reqDto: SaveWorkReqDto): Long?
    fun save(workEntity: WorkEntity): Long?
    fun searchWorks(projectId: Long, workName: String, pageable: Pageable): Page<SearchWorksResDto>?
    fun delete(id: Long)
}