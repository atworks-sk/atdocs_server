package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.app.util.StringUtils
import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.dto.method.MethodDto
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }
data class ClazzDeatailDto (
    var id : Long?,
    var clazzName: String,
    var packageName: String,
    var clazzTypeName : String?,
    var projectName : String,
    var filePath : String,
    var fileName : String,
    var line : Long,
    var createDateTime: String?,
    var methodList : ArrayList<MethodDto>?,
    var clazzAnnotationList : ArrayList<ClazzAnnotationDto>,
    // 현재 클래스를 참조한 타 클래스 리스트
    var importedClass : ArrayList<ClazzDto>,
    var importClass: ArrayList<ClazzDto>,
    var filedList : ArrayList<ClazzFiledDto>
//    var filedList : ArrayList<ClazzFiled>
){
    // 추가 생성자 (projectEntity)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity.id,
            clazzEntity.clazzName,
            clazzEntity.packageName,
        clazzEntity.clazzTypeCd?.codeName ?: "N/A",
            clazzEntity.snapshot!!.project!!.projectName,
            StringUtils.getFilePath( clazzEntity.filePath, File.separatorChar.toString()),
            StringUtils.getFileName( clazzEntity.filePath, File.separatorChar.toString()),
            clazzEntity.line,
            DateUtils.convertLocalDateTimeToString(clazzEntity.createdDateTime),
            getMethodList(clazzEntity.methodList),
            getClazzAnnotationList(clazzEntity.annotationList),
            getImportedClass(clazzEntity.importClazzList),
            getImportClass(clazzEntity.importList),
            getFiledList(clazzEntity.filedList)
    )


}
fun getImportedClass(importClazzList: Collection<ClazzImportEntity>?): ArrayList<ClazzDto> {
    var resList: ArrayList<ClazzDto> = ArrayList<ClazzDto>()
    importClazzList!!.map {
        resList.add(ClazzDto(it.clazz))
    }
    return resList
}

fun getImportClass(importClazzList: Collection<ClazzImportEntity>?): ArrayList<ClazzDto> {
    var resList: ArrayList<ClazzDto> = ArrayList<ClazzDto>()
    importClazzList!!.map { it ->
//        var packageName : String = StringUtils.getFilePath(it.clazzImportName , ".")
//        var clazzName : String = StringUtils.getFileName(it.clazzImportName , ".")
        if(it.importClazz != null){
            it.importClazz.let {
                if(it != null){
                    resList.add( ClazzDto(it) )
                }
            }
        }

    }
    return resList
}


fun getMethodList( methodEntityList : Collection<MethodEntity>?) : ArrayList<MethodDto> {
    var resList: ArrayList<MethodDto> = ArrayList<MethodDto>()
    methodEntityList!!.map {
        resList.add(MethodDto(it))
    }
    return resList
}

fun getFiledList( filedEntityList : Collection<ClazzFiledEntity>?) : ArrayList<ClazzFiledDto> {
    var resList: ArrayList<ClazzFiledDto> = ArrayList<ClazzFiledDto>()
    filedEntityList!!.map {
        resList.add(ClazzFiledDto(it))
    }
    return resList
}


fun getClazzAnnotationList( annotationList : Collection<ClazzAnnotationEntity>?) : ArrayList<ClazzAnnotationDto> {
    var resList: ArrayList<ClazzAnnotationDto> = ArrayList<ClazzAnnotationDto>()
    annotationList!!.map {
        resList.add(ClazzAnnotationDto(it))
    }
    return resList
}