package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.MethodEntity

data class MethodListDto (
    var id : Long?,
    var methodName : String?,
    var accessSpecifier : String?,
    var returnText : String?,
    var line : Long?,
    var clazzName: String?,
    var packageName: String?,
    var projectName : String?,
    var fullContents : String?,
    var createDateTime: String?
){
    // 추가 생성자 (projectEntity)
    constructor(methodEntity: MethodEntity) : this (
            methodEntity.id!!,
            methodEntity.methodName,
            methodEntity.accessSpecifier,
            methodEntity.returnText,
            methodEntity.line,
            methodEntity.clazz.clazzName,
            methodEntity.clazz.packageName,
            methodEntity.snapshot.project!!.projectName,
            methodEntity.fullContents,
            DateUtils.convertLocalDateTimeToString(methodEntity.createdDateTime)
    )
}
