package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzImportEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.MethodReturnEntity
import com.sk.atdocs.dto.clazz.ClazzListDto

data class MethodReturnDto (
    var id : Long?,
    var elementClazzId : Long?,
    var elementName : String,
    var elementDepth : Long
){
    // 추가 생성자 (MethodEntity)
//    constructor(methodReturnEntity: MethodReturnEntity) : this (
//        methodReturnEntity.id!!,
//        if(methodReturnEntity.elementClazz == null) 0 else methodReturnEntity.elementClazz?.id,
//        methodReturnEntity.elementName,
//        methodReturnEntity.elementDepth
//    )
}
