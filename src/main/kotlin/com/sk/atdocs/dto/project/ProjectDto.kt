package com.sk.atdocs.dto.project

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ProjectEntity

data class ProjectDto(
    var id : Long?,
    val projectName: String?,
    var packageName: String?,
    var createDate: String?,
    var modifyDate: String?,
    var snapshotCnt: Int?
){
    // 추가 생성자 (projectEntity)
    constructor(projectEntity: ProjectEntity) : this (
            projectEntity.id,
            projectEntity.projectName,
            projectEntity.packageName,
            DateUtils.convertLocalDateTimeToString(projectEntity.createdDateTime, "yyyy-MM-dd"),
            DateUtils.convertLocalDateTimeToString(projectEntity.modifiedDateTime, "yyyy-MM-dd"),
            projectEntity.snapshotList!!.size
    )
}
