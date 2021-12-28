package com.sk.atdocs.dto.method

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.MethodCallEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.MethodParamEntity
import com.sk.atdocs.domain.entity.MethodReturnEntity

// Mehtod 상세 이력 조회
data class MethodDto (

    // Method 정보
    var id : Long?,
    var methodName : String?,
    var accessSpecifier : String?,
    var line : Long?,

    // Clazz 정보
    var clazzId : Long?,
    var clazzName: String?,
    var packageName: String?,
    var fileTypeName : String?,
    var clazzTypeName : String?,

    // Method 정보 2
    var projectName : String?,
    var comment : String?,
    var fullContents : String?,
    var createDateTime: String?,
    
    // 메서드 리턴 인자
    var methodReturn : MethodReturnDto?,

    // 메서드 파라메터 인자S
    var paramList : ArrayList<MethodParamDto>?,

    // 메서드 Call 리스트
    var callList : ArrayList<MethodCallDto>?
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
            getMethodReturn(methodEntity.methodReturn),
            // 매서드 파라매터 리스트
            getMethodParamList(methodEntity.methodParamList),
            // 매서드 파라매터 리스트
            getMethodCallList(methodEntity.callList)
    )
}

private fun getMethodCallList(list: MutableList<MethodCallEntity>?): ArrayList<MethodCallDto>? {
    var resList: ArrayList<MethodCallDto> = ArrayList<MethodCallDto>()
    list!!.map {
        resList.add(MethodCallDto(it))
    }
    return resList
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
