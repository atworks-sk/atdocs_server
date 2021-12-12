package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.ClazzAnnotationEntity
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.dto.clazz.ClazzDeatailDto
import com.sk.atdocs.dto.clazz.ClazzDto
import com.sk.atdocs.dto.clazz.SearchListReqDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ClazzService {

    // SanpshotService에서 호출하는 class 생성 메서드
    fun saveClazz(clazzEntity: ClazzEntity) : ClazzEntity

    // SanpshotService에서 호출하는 class annotation 생성 메서드
    fun saveClazzAnnotation(clazzAnnotationEntity: ClazzAnnotationEntity) : ClazzAnnotationEntity

    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<ClazzDto>?

    fun searchDetail(id: Long): ClazzDeatailDto?
}