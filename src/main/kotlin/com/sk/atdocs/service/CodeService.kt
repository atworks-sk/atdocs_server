package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.CodeEntity
import com.sk.atdocs.domain.entity.MethodArgsEntity
import com.sk.atdocs.dto.sample.SampleDto

interface CodeService {

    fun findByCode(codeGroup : String, codeKey : String): CodeEntity
}