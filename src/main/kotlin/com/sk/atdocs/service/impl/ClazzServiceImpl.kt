package com.sk.atdocs.service.impl

import com.sk.atdocs.app.exception.CommonException
import com.sk.atdocs.app.exception.ErrorCode
import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.domain.repository.ClazzAnnotationRepository
import com.sk.atdocs.domain.repository.ClazzFiledRepository
import com.sk.atdocs.domain.repository.ClazzImportRepository
import com.sk.atdocs.domain.repository.ClazzRepository
import com.sk.atdocs.dto.clazz.ClazzDeatailDto
import com.sk.atdocs.dto.clazz.ClazzDto
import com.sk.atdocs.dto.clazz.SearchListReqDto
import com.sk.atdocs.service.ClazzService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val logger = KotlinLogging.logger {  }

/*
 * Clazz, ClazzAnnotation 등을 관리
 */
@Service
class ClazzServiceImpl(
    var clazzRepository: ClazzRepository,
    var clazzAnnotationRepository: ClazzAnnotationRepository,
    var clazzImportRepository: ClazzImportRepository,
    var clazzFiledRepository: ClazzFiledRepository
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
//    override fun saveClazzAnnotation(clazzAnnotationEntity: ClazzAnnotationEntity): ClazzAnnotationEntity {
//        return clazzAnnotationRepository.save(clazzAnnotationEntity)
//    }
//
//    override fun saveClazzImport(clazzImportEntity: ClazzImportEntity): ClazzImportEntity {
//        return clazzImportRepository.save(clazzImportEntity)
//    }



    /*
     * 클래스 화면에서 호출한 클래스 리스트 조회 API
     */
    @Transactional(readOnly = true)
    override fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<ClazzDto>? {

        // clazz name like select
        fun likeProjectName(clazzName: String?): Specification<ClazzEntity> {
            return Specification<ClazzEntity> { root, query, builder ->
                if (clazzName!!.isNotBlank()) {
                    builder.like(root.get("clazzName"), "%" + clazzName + "%")
                } else {
                    null
                }
            }
        }

        // project id equal
        fun equalProjectId(projectId: Long?): Specification<ClazzEntity> {
            return Specification<ClazzEntity> { root, query, builder ->
                projectId?.let {
                    builder.equal(root.get<Any>("snapshot")!!.get<Any>("project")!!.get<Any>("id"), it)
                }
                ?: null
            }
        }

        var entities = clazzRepository.findAll( likeProjectName(reqDto.clazzName)
                .and( equalProjectId(reqDto.projectId) )
                , pageable)

        return entities.map { clazzEntity: ClazzEntity? ->
            ClazzDto(clazzEntity!!)
        }
    }

    /*
     * 클래스상세 화면에서 호출한 클래스 상세 조회 API
     */
    @Transactional(readOnly = true)
    override fun searchDetail(id: Long): ClazzDeatailDto? {
         return ClazzDeatailDto(clazzRepository.findById(id).get())
    }

    override fun searchClazzByFilePath(snapshotEntity: SnapshotEntity, filePath: String): Optional<ClazzEntity>? {
        return clazzRepository.findBySnapshotAndFilePath(snapshotEntity, filePath)
    }

    override fun searchClazzById(id: Long): Optional<ClazzEntity>? {
        return clazzRepository.findById(id)
    }

    override fun searchClazzByClazzFullName(snapshotEntity: SnapshotEntity, clazzFullName: String): Optional<ClazzEntity>? {
        return clazzRepository.findBySnapshotAndClazzFullName(snapshotEntity, clazzFullName)
    }

}