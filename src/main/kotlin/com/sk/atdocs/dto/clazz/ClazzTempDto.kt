package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.app.util.StringUtils
import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.dto.method.MethodListDto
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }
data class ClazzTempDto (
    var id : Long?,
    var clazzName: String,
    var packageName: String,
    var fileTypeName : String?,
    var clazzTypeName : String?,
    var projectName : String
){
    // 추가 생성자 (projectEntity)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity.id,
            clazzEntity.clazzName,
            clazzEntity.packageName,
            clazzEntity.fileTypeCd?.codeName ?: "",
            clazzEntity.clazzTypeCd?.codeName ?: "",
            clazzEntity.snapshot!!.project!!.projectName
    )


}