package com.sk.atdocs.dto.snapshot

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.SnapshotEntity

data class SnapshotDto (
    var id : Long?,
    var dirPath : String?,
    var projectName : String?,
    var clazzCnt : Int?,
    var methodCnt : Int?,
    var errorCnt : Int?,
    var createdDateTime: String?,
    var modifiedDateTime: String?
){
    // 추가 생성자 (MethodEntity)
    constructor(snapshotEntity: SnapshotEntity) : this (
            snapshotEntity.id!!,
            snapshotEntity.dirPath,
            snapshotEntity.project?.projectName,
            snapshotEntity.clazzList?.size,
            snapshotEntity.methodList?.size,
            snapshotEntity.snapshotErrorList?.size,
            DateUtils.convertLocalDateTimeToString(snapshotEntity.createdDateTime),
            DateUtils.convertLocalDateTimeToString(snapshotEntity.modifiedDateTime)
    )
}