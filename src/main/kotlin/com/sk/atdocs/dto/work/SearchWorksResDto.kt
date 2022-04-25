package com.sk.atdocs.dto.work

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.WorkEntity

data class SearchWorksResDto(
    var id : Long?,
    val workName: String?,
    var packagePath: String?,
    var javaFileCnt: Long?,
    var xmlFileCnt: Long?,
    var createdDate: String?,
    var modifyDate: String?
){
    constructor(work: WorkEntity) : this (
            work.id,
            work.workName,
            work.packagePath,
            work.javaFileCnt,
            work.xmlFileCnt,
            DateUtils.convertLocalDateTimeToString(work.createdDateTime, "yyyy-MM-dd"),
            DateUtils.convertLocalDateTimeToString(work.modifiedDateTime, "yyyy-MM-dd")
    )
}
