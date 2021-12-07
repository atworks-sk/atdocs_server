package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.ClazzAnnotationEntity
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.SampleEntity
import com.sk.atdocs.domain.repository.ClazzAnnotationRepository
import com.sk.atdocs.domain.repository.ClazzRepository
import com.sk.atdocs.domain.repository.SampleRepository
import com.sk.atdocs.dto.sample.SampleDto
import com.sk.atdocs.service.ClazzService
import com.sk.atdocs.service.SampleService

import mu.KotlinLogging
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


}