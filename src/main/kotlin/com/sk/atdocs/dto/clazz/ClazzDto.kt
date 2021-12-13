package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.ProjectEntity

data class ClazzDto (
    var id : Long?,
    var clazzName: String,
    var line : Long,
    var packageName: String,
    var projectName : String,
    var createDateTime: String?,
    var methodCnt: Int?
){
    // 추가 생성자 (projectEntity)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity.id,
            clazzEntity.clazzName,
            clazzEntity.line,
            clazzEntity.packageName,
            clazzEntity.snapshot!!.project!!.projectName,
            DateUtils.convertLocalDateTimeToString(clazzEntity.createdDateTime),
            clazzEntity.methodList!!.size
//        clazzEntity.methodList!!.stream().
    )
}
