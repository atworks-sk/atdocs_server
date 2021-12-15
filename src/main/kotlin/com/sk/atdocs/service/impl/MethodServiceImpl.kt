package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.MethodArgsEntity
import com.sk.atdocs.domain.repository.MethodArgsRepository
import com.sk.atdocs.domain.repository.MethodRepository
import com.sk.atdocs.dto.method.MethodDto
import com.sk.atdocs.dto.method.SearchListReqDto
import com.sk.atdocs.service.MethodService

import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {  }
@Service
class MethodServiceImpl(
    var methodRepository: MethodRepository,
    var methodArgsRepository: MethodArgsRepository
): MethodService {

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