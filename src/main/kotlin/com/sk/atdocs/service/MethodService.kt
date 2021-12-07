package com.sk.atdocs.service

import com.sk.atdocs.domain.entity.MethodEntity

interface MethodService {
    // SanpshotService에서 호출하는 Metho 생성 메서드
    fun createMethod(methodEntity: MethodEntity) : MethodEntity


}