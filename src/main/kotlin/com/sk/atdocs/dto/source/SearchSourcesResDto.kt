package com.sk.atdocs.dto.source

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.SourceEntity
import com.sk.atdocs.domain.entity.WorkEntity

data class SearchSourcesResDto(
    var id : Long?,
    val fileName: String?,
    var sourceType: String?,
    var createdDate: String?,
    var modifyDate: String?
){
    constructor(source: SourceEntity) : this (
            source.id,
            source.fileName,
            source.sourceType?.value,
            DateUtils.convertLocalDateTimeToString(source.createdDateTime, "yyyy-MM-dd"),
            DateUtils.convertLocalDateTimeToString(source.modifiedDateTime, "yyyy-MM-dd")
    )
}
