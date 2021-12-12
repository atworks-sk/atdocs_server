package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.dto.method.MethodDto
import mu.KotlinLogging

private val logger = KotlinLogging.logger {  }
data class ClazzDeatailDto (
    var id : Long?,
    var clazzName: String,
    var packageName: String,
    var projectName : String,
    var createDateTime: String?,
    var methodList : ArrayList<MethodDto>?
){
    // 추가 생성자 (projectEntity)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity.id,
            clazzEntity.clazzName,
            clazzEntity.packageName,
            clazzEntity.snapshot!!.project!!.projectName,
            DateUtils.convertLocalDateTimeToString(clazzEntity.createdDateTime),
            getMethodList(clazzEntity.methodList)
    )
}

fun getMethodList( methodEntityList : Collection<MethodEntity>?) : ArrayList<MethodDto> {
    var resList: ArrayList<MethodDto> = ArrayList<MethodDto>()
    methodEntityList!!.map {
        resList.add(MethodDto(it))
    }
    return resList

}