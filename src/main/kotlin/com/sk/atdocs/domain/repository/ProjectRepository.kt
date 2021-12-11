package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.entity.SampleEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProjectRepository : PagingAndSortingRepository<ProjectEntity, Long>, JpaSpecificationExecutor<ProjectEntity> {

    fun findByProjectName(projectName:String): Optional<ProjectEntity>

    fun findByProjectNameLike(projectName: String, pageable: Pageable): Page<ProjectEntity>

}