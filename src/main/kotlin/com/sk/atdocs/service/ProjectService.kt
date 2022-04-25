package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.dto.project.ProjectDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProjectService {
    fun findById(projectId: Long) : ProjectEntity
    fun searchList(projectName: String, pageable: Pageable): Page<ProjectDto>?

    fun save(reqDto: ProjectDto): Long?
    fun delete(projectId: Long): Long?
    fun searchListWithoutPage(): ArrayList<ProjectDto>?

    fun searchProjectDetail(projectId: Long): ProjectDto

}