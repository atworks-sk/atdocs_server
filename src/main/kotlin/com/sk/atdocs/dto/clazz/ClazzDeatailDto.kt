package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ClazzAnnotationEntity
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.ClazzImportEntity
import com.sk.atdocs.domain.entity.MethodEntity
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
    var importClass: ArrayList<ClazzDto>
){
    // 추가 생성자 (projectEntity)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity.id,
            clazzEntity.clazzName,
            clazzEntity.packageName,
        clazzEntity.clazzTypeCd?.codeName ?: "N/A",
            clazzEntity.snapshot!!.project!!.projectName,
            getFilePath( clazzEntity.filePath),
            getFileName( clazzEntity.filePath),
            clazzEntity.line,
            DateUtils.convertLocalDateTimeToString(clazzEntity.createdDateTime),
            getMethodList(clazzEntity.methodList),
            getClazzAnnotationList(clazzEntity.annotationList),
            getImportedClass(clazzEntity.importClazzList),
            getImportClass(clazzEntity.importList)
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
        it.importClazz.let {
            resList.add(ClazzDto(it!!))
        }

    }
    return resList
}

fun getFilePath (filePath : String?) : String {
    return try {
        filePath!!.substring(0, filePath.lastIndexOf(File.separatorChar))
    }
    catch (e: Exception){
        ""
    }
}

fun getFileName (filePath : String?) : String {
    return try {
        filePath!!.substring(filePath.lastIndexOf(File.separatorChar)+1, filePath.length)
    }
    catch (e: Exception){
        ""
    }
}
fun getMethodList( methodEntityList : Collection<MethodEntity>?) : ArrayList<MethodDto> {
    var resList: ArrayList<MethodDto> = ArrayList<MethodDto>()
    methodEntityList!!.map {
        resList.add(MethodDto(it))
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