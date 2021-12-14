package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.CodeEntity
import com.sk.atdocs.domain.entity.SampleEntity
import com.sk.atdocs.domain.entity.SnapshotEntity
import com.sk.atdocs.domain.entity.SnapshotErrorEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SnapshotErrorRepository : PagingAndSortingRepository<SnapshotErrorEntity, Long>, JpaSpecificationExecutor<SnapshotErrorEntity> {

    fun findBySnapshotAndFilePath(snapshot: SnapshotEntity, filePath: String): Optional<SnapshotErrorEntity>?

//    fun findByCodeGroupAndCodeKey(codeGroup: String?, codeKey: String?): Optional<SnapshotErrorEntity>?
}