package com.sk.atdocs.app.initializer

//import com.sk.atdocs.app.enum.CodeGroup
import com.sk.atdocs.domain.entity.CodeEntity
import com.sk.atdocs.domain.entity.ProjectEntity
import com.sk.atdocs.domain.repository.CodeRepository
import com.sk.atdocs.domain.repository.ProjectRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


private val logger = KotlinLogging.logger {  }

/*
 * app 구동시 호출되는 클래스
 */
@Component(value = "AppInitializer")
class AppInitializer(
    var projectRepository : ProjectRepository,
    var codeRepository : CodeRepository
) {

    @PostConstruct
    fun postConstruct() {
        if(!projectRepository.findById(1L).isPresent){
            projectRepository.save(ProjectEntity("atWorks - 테스트 자동화 시스템", "com.skcc.atworks"))
        }

        getCodeList()?.let { saveCode(it) }
    }

    fun saveCode( codeList : List<CodeEntity> ) {
        codeList.forEach { codeEntity: CodeEntity? ->
            if(codeRepository.findByCodeGroupAndCodeKey(codeEntity!!.codeGroup, codeEntity!!.codeKey)!!.isEmpty){
                codeRepository.save(codeEntity)
            }
        }
    }

    fun getCodeList(): List<CodeEntity>? {
        val codeList: MutableList<CodeEntity> = ArrayList<CodeEntity>()
        // reg_cd
        codeList.add(CodeEntity("clazzType", "Controller", "Controller"))
        codeList.add(CodeEntity("clazzType", "Data", "Data"))
        codeList.add(CodeEntity("clazzType", "Repository", "Repository"))  // JPA TABLE
        codeList.add(CodeEntity("clazzType", "Entity", "Entity"))  // JPA TABLE

        return codeList
    }
}