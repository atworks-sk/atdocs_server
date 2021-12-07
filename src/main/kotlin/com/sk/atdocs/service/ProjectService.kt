package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.ProjectEntity

interface ProjectService {
    fun findById(projectId: Long) : ProjectEntity
}