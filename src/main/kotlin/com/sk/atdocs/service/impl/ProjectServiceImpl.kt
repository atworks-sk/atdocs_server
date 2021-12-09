package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.repository.ProjectRepository
import com.sk.atdocs.dto.project.ProjectDto
import com.sk.atdocs.dto.project.SearchListReqDto
import com.sk.atdocs.service.ProjectService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Function


private val logger = KotlinLogging.logger {  }
@Service
class ProjectServiceImpl(
    var projectRepository : ProjectRepository
): ProjectService {

    /*
     * project id로 ProjectEntity 조회
     */
    override fun findById(projectId: Long): ProjectEntity {

        var res = projectRepository.findById(projectId)
        return res.get()
    }

    @Transactional(readOnly = true)
    override fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<ProjectDto>? {
        var entities = projectRepository.findByProjectNameLike("%"+reqDto.projectName+"%", pageable);

        return entities.map { projectEntity: ProjectEntity? ->
            ProjectDto(projectEntity!!)
        }
    }



}