package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.ClazzImportEntity
import com.sk.atdocs.domain.entity.ProjectEntity

data class ClazzDetailImportDto (

    var clazzId : Long?,
    var packageName: String,
    var clazzName: String,
){
    // 추가 생성자 (피호출)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity.id,
            clazzEntity.clazzName,
            clazzEntity.packageName
    )

//    // 추가 생성자 (호출)
//    constructor(clazzImportEntity: ClazzImportEntity) : this (
//        clazzImportEntity.importClazz.importClazz
//        clazzEntity.clazzName,
//        clazzEntity.packageName
//    )
}
