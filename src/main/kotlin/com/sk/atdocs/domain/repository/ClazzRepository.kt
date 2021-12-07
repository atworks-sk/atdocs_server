package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.SampleEntity
import com.sk.atdocs.domain.entity.SnapshotEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ClazzRepository : PagingAndSortingRepository<ClazzEntity, Long>, JpaSpecificationExecutor<ClazzEntity> {
}