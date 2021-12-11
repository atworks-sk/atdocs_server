package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.ClazzAnnotationEntity
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.repository.ClazzAnnotationRepository
import com.sk.atdocs.domain.repository.ClazzRepository
import com.sk.atdocs.dto.clazz.ClazzDto
import com.sk.atdocs.dto.clazz.SearchListReqDto
import com.sk.atdocs.service.ClazzService

import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {  }

/*
 * Clazz, ClazzAnnotation 등을 관리
 */
@Service
class ClazzServiceImpl(
    var clazzRepository: ClazzRepository,
    var clazzAnnotationRepository: ClazzAnnotationRepository
): ClazzService {

    /*
     * SanpshotService에서 호출하는 class 생성 메서드
     */
    override fun saveClazz(clazzEntity: ClazzEntity): ClazzEntity {
        return clazzRepository.save(clazzEntity)
    }

    /*
     * SanpshotService에서 호출하는 class annotation 생성 메서드
     */
    override fun saveClazzAnnotation(clazzAnnotationEntity: ClazzAnnotationEntity): ClazzAnnotationEntity {
        return clazzAnnotationRepository.save(clazzAnnotationEntity)
    }

    /*
     * 클래스 화면에서 호출한 클래스 리스트 조회 API
     */
    override fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<ClazzDto>? {

        var entities = clazzRepository.findByClazzNameLike("%%", pageable);
        return entities.map { clazzEntity: ClazzEntity? ->
            ClazzDto(clazzEntity!!)
        }
    }


}