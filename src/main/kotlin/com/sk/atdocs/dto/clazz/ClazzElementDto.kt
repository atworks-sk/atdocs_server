package com.sk.atdocs.dto.clazz

import com.sk.atdocs.domain.entity.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {  }
data class ClazzElementDto (
    var clazz : ClazzEntity?,
    var packageName: String,
    var clazzName: String
){
    // 추가 생성자 (projectEntity)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity,
            clazzEntity.clazzName,
            clazzEntity.packageName
    )


}