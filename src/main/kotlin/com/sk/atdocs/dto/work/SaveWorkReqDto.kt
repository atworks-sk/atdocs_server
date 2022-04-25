package com.sk.atdocs.dto.work

data class SaveWorkReqDto(
        var id : Long,
        var projectId : Long,
        val workName: String,
        var packagePath: String?
)