package com.sk.atdocs.service.impl

import com.sk.atdocs.app.exception.CommonException
import com.sk.atdocs.app.exception.ErrorCode
import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.repository.ProjectRepository
import com.sk.atdocs.dto.SaveResDto
import com.sk.atdocs.dto.project.ProjectDto
import com.sk.atdocs.dto.project.SearchListReqDto
import com.sk.atdocs.service.ProjectService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


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

    /*
     * Search Project List
     */
    @Transactional(readOnly = true)
    override fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<ProjectDto>? {
        var entities = projectRepository.findByProjectNameLike("%"+reqDto.projectName+"%", pageable);
        return entities.map { projectEntity: ProjectEntity? ->
            ProjectDto(projectEntity!!)
        }
    }

    /*
     * Save Project
     */
    @Transactional(readOnly = false)
    override fun save(reqDto: ProjectDto): Long? {
        // id가 -1인경우 등록, 아니면 수정작업이 필요합니다.
        var id = reqDto.id
        var projectEntity: ProjectEntity? = null;
        if( id == -1L){
            projectEntity = ProjectEntity(reqDto.projectName!!, reqDto.packageName)
        }
        else{
            projectEntity = projectRepository.findById(reqDto.id!!).get()
            projectEntity!!.projectName = reqDto.projectName!!
            projectEntity!!.packageName = reqDto.packageName!!
        }

        projectEntity = projectRepository.save(projectEntity)
            ?.let {
                return it.id
            }
            ?: throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
    }

    /*
     * Delete Project
     */
    override fun delete(projectId: Long): Long? {
        val projectEntity = projectRepository.findByIdOrNull(projectId)
            ?.let {
                projectRepository.delete(it)
                return it.id
            }
            ?: throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
    }

    /*
     * Search Project List without page
     */
    @Transactional(readOnly = false)
    override fun searchListWithoutPage(): ArrayList<ProjectDto>? {
        var projectList : ArrayList<ProjectDto>? = ArrayList<ProjectDto>()
        projectRepository.findAll().forEach { projectEntity ->
            logger.info(projectEntity.projectName)
            projectList!!.add(ProjectDto(projectEntity))
        }
        return projectList
    }

}