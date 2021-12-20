package com.sk.atdocs.dto.clazz

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.app.util.StringUtils
import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.dto.method.MethodListDto
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }
data class ClazzDto (
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
    var importedClass : ArrayList<ClazzTempDto>,
    var importClass: ArrayList<ClazzTempDto>,
    var filedList : ArrayList<ClazzFiledDto>,
    var inheritanceList :  ArrayList<ClazzTempDto>,
    var inheritedList :  ArrayList<ClazzTempDto>
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
            getInheritancedListList(clazzEntity.inheritedList),
    )


}

fun getInheritanceListList(inheritanceList: Collection<ClazzInheritanceEntity>?): ArrayList<ClazzTempDto> {
    var resList: ArrayList<ClazzTempDto> = ArrayList<ClazzTempDto>()
    inheritanceList!!.map {
        if(it.inheritanceClazz != null){
            resList.add(ClazzTempDto(it.inheritanceClazz!!))
        }
        else{
            resList.add(
                ClazzTempDto(
                    0,
                    it.clazzName,
                    it.packageName,
                    "".toString(),
                    "".toString(),
                    it.snapshot.project!!.projectName

                )
            )
        }
    }
    return resList
}

fun getInheritancedListList(inheritanceClazzList: Collection<ClazzInheritanceEntity>?): ArrayList<ClazzTempDto> {
    var resList: ArrayList<ClazzTempDto> = ArrayList<ClazzTempDto>()
    inheritanceClazzList!!.map { it ->
        if(it.clazz != null){
            it.clazz.let {
                if(it != null){
                    resList.add( ClazzTempDto(it) )
                }
            }
        }

    }
    return resList
}

fun getImportedClass(importClazzList: Collection<ClazzImportEntity>?): ArrayList<ClazzTempDto> {
    var resList: ArrayList<ClazzTempDto> = ArrayList<ClazzTempDto>()
    importClazzList!!.map {
        resList.add(ClazzTempDto(it.clazz))
    }
    return resList
}

fun getImportClass(importClazzList: Collection<ClazzImportEntity>?): ArrayList<ClazzTempDto> {
    var resList: ArrayList<ClazzTempDto> = ArrayList<ClazzTempDto>()
    importClazzList!!.map { it ->
        if(it.importClazz != null){
            it.importClazz.let {
                if(it != null){
                    resList.add( ClazzTempDto(it) )
                }
            }
        }
        else{
            resList.add(
                ClazzTempDto(
                    0,
                    it.clazzName,
                    it.packageName,
                    "".toString(),
                    "".toString(),
                    it.snapshot.project!!.projectName

                )
            )
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
