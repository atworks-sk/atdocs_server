package com.sk.atdocs.domain.repository

import com.sk.atdocs.domain.entity.SampleEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleRepository : PagingAndSortingRepository<SampleEntity, Long>, JpaSpecificationExecutor<SampleEntity> {
    fun findByName(name:String):List<SampleEntity>
    fun findAllBy(): List<SampleEntity>

}