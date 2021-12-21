package com.sk.atdocs.dto.clazz

data class ClazzFiledElementDto (
    var id : Long?,
    var elementClazzId : Long?,
    var elementName : String,
    var elementDepth : Long
){

//    // 추가 생성자 (피호출)
//    constructor(clazzFiledElementEntity: ClazzFiledElementEntity) : this (
//        clazzFiledElementEntity.id,
//        if(clazzFiledElementEntity.elementClazz == null) 0 else clazzFiledElementEntity.elementClazz?.id,
//        clazzFiledElementEntity.elementName,
//        clazzFiledElementEntity.elementDepth
//    )

}
