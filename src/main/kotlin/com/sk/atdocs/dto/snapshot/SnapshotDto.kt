package com.sk.atdocs.dto.snapshot

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.SnapshotEntity
import com.sk.atdocs.dto.method.getMethodParamList
import com.sk.atdocs.dto.method.getMethodReturnList

data class SnapshotDto (
    var id : Long?
){
    // 추가 생성자 (MethodEntity)
    constructor(snapshotEntity: SnapshotEntity) : this (
            snapshotEntity.id!!
    )
}