package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.SampleEntity
import com.sk.atdocs.domain.repository.ClazzRepository
import com.sk.atdocs.domain.repository.MethodRepository
import com.sk.atdocs.domain.repository.SampleRepository
import com.sk.atdocs.dto.clazz.ClazzDto
import com.sk.atdocs.dto.method.MethodDto
import com.sk.atdocs.dto.method.SearchListReqDto
import com.sk.atdocs.dto.sample.SampleDto
import com.sk.atdocs.service.ClazzService
import com.sk.atdocs.service.MethodService
import com.sk.atdocs.service.SampleService

import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    /*
     * 메서드 리스트 조회 (pageable)
     */
    @Transactional(readOnly = true)
    override fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<MethodDto>? {
        // project id equal
        fun equalProjectId(projectId: Long?): Specification<MethodEntity> {
            return Specification<MethodEntity> { root, query, builder ->
                projectId?.let {
                    builder.equal(root.get<Any>("snapshot")!!.get<Any>("project")!!.get<Any>("id"), it)
                }
                        ?: null
            }
        }

        // clazz name like select
        fun likeMethodName(methodName: String?): Specification<MethodEntity> {
            return Specification<MethodEntity> { root, query, builder ->
                if (methodName!!.isNotBlank()) {
                    builder.like(root.get("methodName"), "%" + methodName + "%")
                } else {
                    null
                }
            }
        }
        var entities = methodRepository.findAll(likeMethodName(reqDto.methodName)
                .and( equalProjectId(reqDto.projectId) )
                , pageable)
        return entities.map { methodEntity: MethodEntity? ->
            MethodDto(methodEntity!!)
        }
    }


}