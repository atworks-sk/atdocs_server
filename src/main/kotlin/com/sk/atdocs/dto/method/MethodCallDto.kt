package com.sk.atdocs.dto.method

import com.sk.atdocs.domain.entity.*

data class MethodCallDto (
    var id : Long?,
    var scope : String?,
    var name : String,
    var argumentCnt : Int
){
    // 추가 생성자 (MethodParamEntity)
    constructor(entity: MethodCallEntity) : this (
        entity.id!!,
        entity.scope,
        entity.name,
        entity.argumentCnt
    )
}

//private fun getMethodParamTypeList( list: Collection<MethodParamTypeEntity>?): ArrayList<MethodParamTypeDto> {
//    var resList: ArrayList<MethodParamTypeDto> = ArrayList<MethodParamTypeDto>()
//    list!!.map {
//        resList.add(MethodParamTypeDto(it))
//    }
//    return resList
//}