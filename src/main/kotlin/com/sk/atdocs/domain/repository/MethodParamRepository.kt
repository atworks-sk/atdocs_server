package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.*
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MethodParamRepository : PagingAndSortingRepository<MethodParamEntity, Long>, JpaSpecificationExecutor<MethodParamEntity> {
}