package com.sk.atdocs.service.impl

import com.github.javaparser.StaticJavaParser
import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.repository.ProjectRepository
import com.sk.atdocs.service.ProjectService
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File


private val logger = KotlinLogging.logger {  }
@Service
class ProjectServiceImpl(
    var projectRepository : ProjectRepository
): ProjectService {

    /*
     * 자바 소스를 분석/번역 시스템
     */
    override fun findById(projectId: Long): ProjectEntity {

        var res = projectRepository.findById(projectId)
        return res.get()
    }

}