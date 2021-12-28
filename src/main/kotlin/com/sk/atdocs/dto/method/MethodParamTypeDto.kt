package com.sk.atdocs.dto.method

import com.sk.atdocs.domain.entity.MethodParamTypeEntity

data class MethodParamTypeDto (
    var id : Long?,
    var clazzId : Long?,
    var clazzName : String,
    var packageName : String?,
    var methodParamTypeList : ArrayList<MethodParamTypeDto>?
){
    // 추가 생성자 (MethodEntity)
    constructor(methodParamTypeEntity: MethodParamTypeEntity) : this (
        methodParamTypeEntity.id!!,
        if(methodParamTypeEntity.myClazz == null) 0 else methodParamTypeEntity.myClazz?.id,
        methodParamTypeEntity.clazzName,
        methodParamTypeEntity.packageName,
        getMethodParamTypeList(methodParamTypeEntity.children)
    )
}

private fun getMethodParamTypeList( list: Collection<MethodParamTypeEntity>?): ArrayList<MethodParamTypeDto> {
    var resList: ArrayList<MethodParamTypeDto> = ArrayList<MethodParamTypeDto>()
    list!!.map {
        resList.add(MethodParamTypeDto(it))
    }
    return resList
}