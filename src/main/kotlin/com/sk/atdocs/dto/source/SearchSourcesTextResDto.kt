package com.sk.atdocs.dto.source

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.SourceEntity
import com.sk.atdocs.domain.entity.WorkEntity

data class SearchSourcesTextResDto(
    var id : Long?,
    val fileName: String?,
    var sourceType: String?,
    var sourceText: String?
){
    constructor(source: SourceEntity) : this (
            source.id,
            source.fileName,
            source.sourceType?.value,
            source.sourceText
    )
}
