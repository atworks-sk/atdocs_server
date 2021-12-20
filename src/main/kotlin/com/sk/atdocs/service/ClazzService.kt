package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.dto.clazz.ClazzDto
import com.sk.atdocs.dto.clazz.ClazzListDto
import com.sk.atdocs.dto.clazz.SearchListReqDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*


interface ClazzService {

    // SanpshotService에서 호출하는 class 생성 메서드
    fun saveClazz(clazzEntity: ClazzEntity) : ClazzEntity

    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<ClazzListDto>?

    fun searchDetail(id: Long): ClazzDto?

    // SanpshotService에서 등록된 clazz 객체를 구함
    fun searchClazzByFilePath( snapshotEntity: SnapshotEntity, fullPath : String ) : Optional<ClazzEntity>?

    fun searchClazzById( id : Long ) : Optional<ClazzEntity>?

    fun searchClazzByClazzFullName( snapshotEntity: SnapshotEntity, clazzFullName : String ) : Optional<ClazzEntity>?



}