package com.sk.atdocs.service.impl

import com.sk.atdocs.app.exception.CommonException
import com.sk.atdocs.app.exception.ErrorCode
import com.sk.atdocs.domain.entity.WorkEntity
import com.sk.atdocs.domain.repository.SourceRepository
import com.sk.atdocs.domain.repository.WorkRepository
import com.sk.atdocs.dto.work.SaveWorkReqDto
import com.sk.atdocs.dto.work.SearchWorksResDto
import com.sk.atdocs.service.CodeService
import com.sk.atdocs.service.ProjectService
import com.sk.atdocs.service.WorkService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

private val logger = KotlinLogging.logger {  }

@Service
class WorkServiceImpl(
        var workRepository : WorkRepository,
        var sourceRepository: SourceRepository,
        var projectService: ProjectService,
        var codeService: CodeService

        ): WorkService {

    override fun save(dto: SaveWorkReqDto): Long? {

        // id가 -1인경우 등록, 아니면 수정작업이 필요합니다.
        var work : WorkEntity? = null;
        if( dto.id == -1L){
            work = WorkEntity(
                    dto.workName,
                    dto.packagePath!!,
                    projectService.findById(dto.projectId)
            );
        }
        else{
            work = workRepository.findById(dto.id!!).get()
            work?.workName = dto.workName;
            work?.packagePath = dto.packagePath;
        }


        workRepository.save(work)?.let {
            return it.id
        }


        throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
    }

    @Transactional()
    override fun save(work: WorkEntity): Long? {
        workRepository.save(work)?.let {
            return it.id
        }
        throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
    }

    override fun searchWorks(projectId: Long, workName: String, pageable: Pageable): Page<SearchWorksResDto>? {
        var works = workRepository.findByProjectIdAndWorkNameLike(projectId, "%$workName%",  pageable);


        return works.map { it ->
            SearchWorksResDto(it!!)
        }
    }

    override fun delete(id: Long) {
        val projectEntity = workRepository.findByIdOrNull(id)
                ?.let {
                    workRepository.delete(it)
                    return;
                }
                ?: throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
    }

}