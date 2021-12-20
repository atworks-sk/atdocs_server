package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzImportEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.MethodParamElementEntity
import com.sk.atdocs.domain.entity.MethodReturnEntity
import com.sk.atdocs.dto.clazz.ClazzListDto

data class MethodParamElementDto (
    var id : Long?,
    var elementClazzId : Long?,
    var elementClazzName : String,
    var elementPackageName : String?,
    var elementDepth : Long
){
    // 추가 생성자 (MethodEntity)
    constructor(methodParamElementEntity: MethodParamElementEntity) : this (
        methodParamElementEntity.id!!,
        if(methodParamElementEntity.elementClazz == null) 0 else methodParamElementEntity.elementClazz?.id,
        methodParamElementEntity.elementClazzName,
        methodParamElementEntity.elementPackageName,
        methodParamElementEntity.elementDepth
    )
}
