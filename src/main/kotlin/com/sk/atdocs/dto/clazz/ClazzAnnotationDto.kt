package com.sk.atdocs.dto.clazz

import com.sk.atdocs.domain.entity.ClazzAnnotationEntity
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.ClazzImportEntity
import com.sk.atdocs.domain.entity.SnapshotEntity
import mu.KotlinLogging

private val logger = KotlinLogging.logger {  }
data class ClazzAnnotationDto(
    var id: Long?,
    var clazzId : Long?,
    var expression : String,
    var annotationName : String,
    var param1 : String?,
    var param2 : String?

){
    // 추가 생성자 (projectEntity)
    constructor(clazzAnnotationEntity: ClazzAnnotationEntity) : this (
        clazzAnnotationEntity.id,
        clazzAnnotationEntity.clazz?.id,
        clazzAnnotationEntity.expression,
        clazzAnnotationEntity.annotationName,
        clazzAnnotationEntity.param1,
        clazzAnnotationEntity.param2
    )
}
