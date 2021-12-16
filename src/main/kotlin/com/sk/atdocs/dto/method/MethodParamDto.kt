package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.dto.clazz.ClazzListDto

data class MethodParamDto (
    var id : Long?,
    var elementText : String,
    var filedName : String,
    var comment : String?,
    var methodParamElementList : ArrayList<MethodParamElementDto>?
){
    // 추가 생성자 (MethodParamEntity)
    constructor(methodParamEntity: MethodParamEntity) : this (
        methodParamEntity.id!!,
        methodParamEntity.elementText,
        methodParamEntity.filedName,
        methodParamEntity.comment,
        getMethodParamElementList(methodParamEntity.methodParamElementList)
//
    )
}
fun getMethodParamElementList( list: Collection<MethodParamElementEntity>?): ArrayList<MethodParamElementDto> {
    var resList: ArrayList<MethodParamElementDto> = ArrayList<MethodParamElementDto>()
    list!!.map {
        resList.add(MethodParamElementDto(it))
    }
    return resList
}