package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.dto.project.ProjectDto
import com.sk.atdocs.dto.project.SearchListReqDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProjectService {
    fun findById(projectId: Long) : ProjectEntity
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<ProjectDto>?
}