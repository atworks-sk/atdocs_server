package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.entity.WorkEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkRepository : PagingAndSortingRepository<WorkEntity, Long>, JpaSpecificationExecutor<WorkEntity> {

    fun findByProjectIdAndWorkNameLike(projectId: Long, s: String, pageable: Pageable): Page<WorkEntity>

}