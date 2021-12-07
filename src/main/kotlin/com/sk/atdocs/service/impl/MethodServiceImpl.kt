package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.SampleEntity
import com.sk.atdocs.domain.repository.ClazzRepository
import com.sk.atdocs.domain.repository.MethodRepository
import com.sk.atdocs.domain.repository.SampleRepository
import com.sk.atdocs.dto.sample.SampleDto
import com.sk.atdocs.service.ClazzService
import com.sk.atdocs.service.MethodService
import com.sk.atdocs.service.SampleService

import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {  }
@Service
class MethodServiceImpl(
    var methodRepository: MethodRepository
): MethodService {

    /*
     * 스냅샷 생성시에 호출하는 메서드 등록 함수
     */
    override fun createMethod(methodEntity: MethodEntity): MethodEntity {
        return methodRepository.save(methodEntity)
    }


}