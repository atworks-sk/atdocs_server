package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.ClazzAnnotationEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ClazzAnnotationRepository : PagingAndSortingRepository<ClazzAnnotationEntity, Long>, JpaSpecificationExecutor<ClazzAnnotationEntity> {

}