package com.sk.atdocs.dto.clazz

import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.ClazzFiledElementEntity
import com.sk.atdocs.domain.entity.ClazzFiledEntity

data class ClazzFiledDto (
    var clazzId : Long? = 0,
    var accessSpecifier : String,
    var comment : String?,
    var expression : String,
    var filedName : String,
    var elementList : ArrayList<ClazzFiledElementDto>
){
    // 추가 생성자 (피호출)
    constructor(clazzFiledEntity: ClazzFiledEntity) : this (
        clazzFiledEntity.clazz.id,
        clazzFiledEntity.accessSpecifier,
        clazzFiledEntity.comment,
        clazzFiledEntity.expression,
        clazzFiledEntity.filedName,
        getFiledElementList(clazzFiledEntity.filedElementList)

    )

}

fun getFiledElementList( filedEntityList : Collection<ClazzFiledElementEntity>?) : ArrayList<ClazzFiledElementDto> {
    var resList: ArrayList<ClazzFiledElementDto> = ArrayList<ClazzFiledElementDto>()
    filedEntityList!!.map {
        resList.add(ClazzFiledElementDto(it))
    }
    return resList
}
