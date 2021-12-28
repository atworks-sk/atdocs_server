package com.sk.atdocs.dto.method

import com.sk.atdocs.domain.entity.*

data class MethodParamDto (
    var id : Long?,
    var name : String,
    var typeText : String,
    var methodParamTypeList : ArrayList<MethodParamTypeDto>?
){
    // 추가 생성자 (MethodParamEntity)
    constructor(methodParamEntity: MethodParamEntity) : this (
        methodParamEntity.id!!,
        methodParamEntity.name,
        methodParamEntity.typeText,
        getMethodParamTypeList(methodParamEntity.typeList)
    )
}

private fun getMethodParamTypeList( list: Collection<MethodParamTypeEntity>?): ArrayList<MethodParamTypeDto> {
    var resList: ArrayList<MethodParamTypeDto> = ArrayList<MethodParamTypeDto>()
    list!!.map {
        resList.add(MethodParamTypeDto(it))
    }
    return resList
}