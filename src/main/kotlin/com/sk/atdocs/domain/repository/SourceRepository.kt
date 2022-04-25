package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.SourceEntity
import com.sk.atdocs.domain.enum.SourceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SourceRepository : PagingAndSortingRepository<SourceEntity, Long> , JpaSpecificationExecutor<SourceEntity> {
     fun findByWorkIdAndFileNameLike(workId: Long, fileName: String, pageable: Pageable):  Page<SourceEntity>
     fun findByWorkIdAndFileNameLikeAndSourceType(workId: Long, s: String, sourceTypeObj: SourceType, pageable: Pageable): Page<SourceEntity>

     fun countByWorkIdAndAndSourceType(workId: Long, sourceType: SourceType):Long
}