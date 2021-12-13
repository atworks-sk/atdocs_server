package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.CodeEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CodeRepository : PagingAndSortingRepository<CodeEntity, Long>, JpaSpecificationExecutor<CodeEntity> {

    fun findByCodeGroupOrderByIdAsc(codeGroup: String?): List<CodeEntity>?

    fun findByCodeGroupAndCodeKey(codeGroup: String?, codeKey: String?): Optional<CodeEntity>?

}