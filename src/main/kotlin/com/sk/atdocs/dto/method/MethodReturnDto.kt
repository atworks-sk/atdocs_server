package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.ClazzImportEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.MethodReturnEntity
import com.sk.atdocs.dto.clazz.ClazzListDto

data class MethodReturnDto (
    var id : Long?,
    var clazzId : Long?,
    var clazzName : String,
    var packageName : String?,
    var methodReturnList : ArrayList<MethodReturnDto>?

//    clazzName : String,
//    packageName : String?,
//    myClazz : ClazzEntity?,
//    parent : MethodReturnEntity?
){
    // 추가 생성자 (MethodEntity)
    constructor(methodReturnEntity: MethodReturnEntity) : this (
        methodReturnEntity.id!!,
        if(methodReturnEntity.myClazz == null) 0 else methodReturnEntity.myClazz?.id,
        methodReturnEntity.clazzName,
        methodReturnEntity.packageName,
        getMethodReturnList(methodReturnEntity.children)
    )
}

private fun getMethodReturnList( list: Collection<MethodReturnEntity>?): ArrayList<MethodReturnDto> {
    var resList: ArrayList<MethodReturnDto> = ArrayList<MethodReturnDto>()
    list?.map {
        resList.add(MethodReturnDto(it))
    }
    return resList
}