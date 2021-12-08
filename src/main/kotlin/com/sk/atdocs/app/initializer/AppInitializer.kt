package com.sk.atdocs.app.initializer

import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.entity.SnapshotEntity
import com.sk.atdocs.domain.repository.ProjectRepository
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


private val logger = KotlinLogging.logger {  }

/*
 * app 구동시 호출되는 클래스
 */
@Component(value = "AppInitializer")
class AppInitializer(
    var projectRepository : ProjectRepository
) {

    @PostConstruct
    fun postConstruct() {
        if(projectRepository.findById(1L).isPresent){
           return
        }
        var project = ProjectEntity("atWorks - 테스트 자동화 시스템")
        var project2 = ProjectEntity("SFTM - 짝퉁 NTM")
        projectRepository.save(project)
        projectRepository.save(project2)
    }
}