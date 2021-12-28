package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.MethodParamEntity
import com.sk.atdocs.domain.entity.MethodReturnEntity

// Mehtod 상세 이력 조회
data class MethodDto (
    var id : Long?,
    var methodName : String?,
    var accessSpecifier : String?,
    var line : Long?,
    var clazzId : Long?,
    var clazzName: String?,
    var packageName: String?,
    var fileTypeName : String?,
    var clazzTypeName : String?,
    var projectName : String?,
    var comment : String?,
    var fullContents : String?,
    var createDateTime: String?,
    var methodReturn : MethodReturnDto?,
    var methodParamList : ArrayList<MethodParamDto>?
){
//    var methodList : ArrayList<MethodDto>?,

    // 추가 생성자 (MethodEntity)
    constructor(methodEntity: MethodEntity) : this (
            methodEntity.id!!,
            methodEntity.methodName,
            methodEntity.accessSpecifier,
            methodEntity.line,
            methodEntity.clazz.id,
            methodEntity.clazz.clazzName,
            methodEntity.clazz.packageName,
            methodEntity.clazz.fileTypeCd?.codeName ?: "",
            methodEntity.clazz.clazzTypeCd?.codeName ?: "",
            methodEntity.snapshot.project!!.projectName,
            methodEntity.comment,
            methodEntity.fullContents,
            DateUtils.convertLocalDateTimeToString(methodEntity.createdDateTime),

            // 매서드 리턴 데이터
//            MethodReturnDto(if(methodEntity.methodReturn == null ) ),
            getMethodReturn(methodEntity.methodReturn),
            // 매서드 파라매터 리스트
            getMethodParamList(methodEntity.methodParamList)

    )




}
private fun getMethodReturn(methodReturn: MethodReturnEntity?): MethodReturnDto? {

    if(methodReturn == null)
        return null
    return MethodReturnDto(methodReturn)
}

private fun getMethodParamList( list: Collection<MethodParamEntity>?): ArrayList<MethodParamDto> {
    var resList: ArrayList<MethodParamDto> = ArrayList<MethodParamDto>()
    list!!.map {
        resList.add(MethodParamDto(it))
    }
    return resList
}
