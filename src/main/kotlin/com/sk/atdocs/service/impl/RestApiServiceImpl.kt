package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.RestApiEntity
import com.sk.atdocs.domain.repository.RestApiRepository
import com.sk.atdocs.dto.rest.RestApiListDto
import com.sk.atdocs.dto.rest.SearchListReqDto
import com.sk.atdocs.service.RestApiService

import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {  }
@Service
class RestApiServiceImpl(
        var restApiRepository: RestApiRepository
): RestApiService {

    /*
     * rest 리스트 조회 (pageable)
     */
    @Transactional(readOnly = true)
    override fun searchList(reqDto: SearchListReqDto, pageable: Pageable) : Page<RestApiListDto>?{
//        TODO("Not yet implemented")

        // project id equal
        fun equalProjectId(projectId: Long?): Specification<RestApiEntity> {
            return Specification<RestApiEntity> { root, query, builder ->
                projectId?.let {
                    builder.equal(root.get<Any>("snapshot")!!.get<Any>("project")!!.get<Any>("id"), it)
                }
                        ?: null
            }
        }

        // urlPath like select
        fun likeUrlPath(urlPath: String?): Specification<RestApiEntity> {
            return Specification<RestApiEntity> { root, query, builder ->
                if (urlPath!!.isEmpty()) {
                    builder.like(root.get("urlPath"), "%" + urlPath + "%")
                } else {
                    null
                }
            }
        }

        var entities = restApiRepository.findAll(likeUrlPath(reqDto.urlPath)
                .and( equalProjectId(reqDto.projectId) )
                , pageable)
        return entities.map { restApiEntity : RestApiEntity? ->
            RestApiListDto(restApiEntity!!)
        }
    }


}