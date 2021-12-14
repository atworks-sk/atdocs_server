package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.CodeEntity
import com.sk.atdocs.domain.repository.CodeRepository
import com.sk.atdocs.service.CodeService
import mu.KotlinLogging
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {  }

@Service
class CodeServiceImpl(
    var codeRepository: CodeRepository
): CodeService {

    override fun findByCode(codeGroup: String, codeKey: String): CodeEntity {
        return codeRepository.findByCodeGroupAndCodeKey(codeGroup, codeKey)!!.get()
    }


}