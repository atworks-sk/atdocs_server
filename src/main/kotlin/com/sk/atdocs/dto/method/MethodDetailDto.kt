package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.ProjectEntity

data class MethodDetailDto (
    var id : Long?,
    var methodName : String?,
    var accessSpecifier : String?,
    var line : Long?,
    var clazzId : Long?,
    var clazzName: String?,
    var packageName: String?,
    var projectName : String?,
    var createDateTime: String?
){
    // 추가 생성자 (projectEntity)
    constructor(methodEntity: MethodEntity) : this (
            methodEntity.id!!,
            methodEntity.methodName,
            methodEntity.accessSpecifier,
            methodEntity.line,
            methodEntity.clazz.id,
            methodEntity.clazz.clazzName,
            methodEntity.clazz.packageName,
            methodEntity.snapshot.project!!.projectName,

            DateUtils.convertLocalDateTimeToString(methodEntity.createdDateTime)
    )
}
