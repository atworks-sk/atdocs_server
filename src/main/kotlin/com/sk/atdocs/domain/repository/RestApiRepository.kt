package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.RestApiEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface RestApiRepository : PagingAndSortingRepository<RestApiEntity, Long>, JpaSpecificationExecutor<RestApiEntity> {

}