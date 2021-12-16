package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.CodeEntity

interface CodeService {

    fun findByCode(codeGroup : String, codeKey : String): CodeEntity
}