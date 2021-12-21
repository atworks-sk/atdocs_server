package com.sk.atdocs.dto.clazz

import com.sk.atdocs.domain.entity.ClazzFieldEntity

data class ClazzFiledDto (
    var clazzId : Long? = 0,
    var accessSpecifier : String,
    var comment : String?,
    var expression : String,
    var filedName : String
//    ,
//    var typeList : ArrayList<ClazzFiledElementDto>
){
    // 추가 생성자 (피호출)
    constructor(clazzFieldEntity: ClazzFieldEntity) : this (
        clazzFieldEntity.clazz.id,
        clazzFieldEntity.accessSpecifier,
        clazzFieldEntity.comment,
        clazzFieldEntity.fullContents,
        clazzFieldEntity.name
//        ,
//        getFiledElementList(clazzFieldEntity.filedElementList)
    )

}

//fun getFiledElementList( filedEntityList : Collection<ClazzFiledElementEntity>?) : ArrayList<ClazzFiledElementDto> {
//    var resList: ArrayList<ClazzFiledElementDto> = ArrayList<ClazzFiledElementDto>()
//    filedEntityList!!.map {
//        resList.add(ClazzFiledElementDto(it))
//    }
//    return resList
//}
