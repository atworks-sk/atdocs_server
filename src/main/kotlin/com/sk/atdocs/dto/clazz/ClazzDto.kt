package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.app.util.StringUtils
import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.dto.method.MethodListDto
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }
data class ClazzDeatailDto (
    var id : Long?,
    var clazzName: String,
    var packageName: String,
    var fileTypeName : String?,
    var clazzTypeName : String?,
    var projectName : String,
    var filePath : String,
    var fileName : String,
    var line : Long,
    var comment : String?,
    var createDateTime: String?,
    var methodList : ArrayList<MethodListDto>?,
    var clazzAnnotationList : ArrayList<ClazzAnnotationDto>,
    var importedClass : ArrayList<ClazzListDto>,
    var importClass: ArrayList<ClazzListDto>,
    var filedList : ArrayList<ClazzFiledDto>,
    var inheritanceList :  ArrayList<ClazzListDto>,
    var inheritancedList :  ArrayList<ClazzListDto>
){
    // 추가 생성자 (projectEntity)
    constructor(clazzEntity: ClazzEntity) : this (
            clazzEntity.id,
            clazzEntity.clazzName,
            clazzEntity.packageName,
            clazzEntity.fileTypeCd?.codeName ?: "",
            clazzEntity.clazzTypeCd?.codeName ?: "",
            clazzEntity.snapshot!!.project!!.projectName,
            StringUtils.getFilePath( clazzEntity.filePath, File.separatorChar.toString()),
            StringUtils.getFileName( clazzEntity.filePath, File.separatorChar.toString()),
            clazzEntity.line,
            clazzEntity.comment,
            DateUtils.convertLocalDateTimeToString(clazzEntity.createdDateTime),


            getMethodList(clazzEntity.methodList),
            getClazzAnnotationList(clazzEntity.annotationList),
            getImportedClass(clazzEntity.importClazzList),
            getImportClass(clazzEntity.importList),
            getFiledList(clazzEntity.filedList),
            getInheritanceListList(clazzEntity.inheritanceList),
            getInheritancedListList(clazzEntity.inheritanceClazzList),
    )


}

fun getInheritanceListList(inheritanceList: Collection<ClazzInheritanceEntity>?): ArrayList<ClazzListDto> {
    var resList: ArrayList<ClazzListDto> = ArrayList<ClazzListDto>()
    inheritanceList!!.map {
        resList.add(ClazzListDto(it.inheritanceClazz!!))
    }
    return resList
}

fun getInheritancedListList(inheritanceClazzList: Collection<ClazzInheritanceEntity>?): ArrayList<ClazzListDto> {
    var resList: ArrayList<ClazzListDto> = ArrayList<ClazzListDto>()
    inheritanceClazzList!!.map { it ->
        if(it.clazz != null){
            it.clazz.let {
                if(it != null){
                    resList.add( ClazzListDto(it) )
                }
            }
        }
    }
    return resList
}

fun getImportedClass(importClazzList: Collection<ClazzImportEntity>?): ArrayList<ClazzListDto> {
    var resList: ArrayList<ClazzListDto> = ArrayList<ClazzListDto>()
    importClazzList!!.map {
        resList.add(ClazzListDto(it.clazz))
    }
    return resList
}

fun getImportClass(importClazzList: Collection<ClazzImportEntity>?): ArrayList<ClazzListDto> {
    var resList: ArrayList<ClazzListDto> = ArrayList<ClazzListDto>()
    importClazzList!!.map { it ->
        if(it.importClazz != null){
            it.importClazz.let {
                if(it != null){
                    resList.add( ClazzListDto(it) )
                }
            }
        }
    }
    return resList
}


fun getMethodList( methodEntityList : Collection<MethodEntity>?) : ArrayList<MethodListDto> {
    var resList: ArrayList<MethodListDto> = ArrayList<MethodListDto>()
    methodEntityList!!.map {
        resList.add(MethodListDto(it))
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

//
