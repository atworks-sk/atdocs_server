package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.SnapshotEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList

@Repository
interface ClazzRepository : PagingAndSortingRepository<ClazzEntity, Long>, JpaSpecificationExecutor<ClazzEntity> {

    fun findByClazzNameLike(clazzName : String, pageable: Pageable): Page<ClazzEntity>

    fun findBySnapshotAndFilePath(snapshot: SnapshotEntity, filePath: String): Optional<ClazzEntity>?
    fun findBySnapshot(snapshot: SnapshotEntity): ArrayList<ClazzEntity>?

    fun findBySnapshotAndClazzFullName(snapshot: SnapshotEntity, clazzFullName: String): Optional<ClazzEntity>?


}