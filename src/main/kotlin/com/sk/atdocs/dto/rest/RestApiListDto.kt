package com.sk.atdocs.dto.rest

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.RestApiEntity

data class RestApiListDto (
    var id : Long?,
    var urlPath : String,
    var httpMethod : String,
    var projectName : String,
    var clazzName : String,
    var methodName : String,
    var createDateTime: String?,
){
    // 추가 생성자 (projectEntity)
    constructor(restApiEntity: RestApiEntity) : this (
            restApiEntity.id!!,
            restApiEntity.urlPath,
            restApiEntity.httpMethod.toString(),
            restApiEntity.snapshot.project!!.projectName,
            restApiEntity.method?.clazz?.clazzName,
            restApiEntity.method?.methodName,
            DateUtils.convertLocalDateTimeToString(restApiEntity.createdDateTime)
    )
}
