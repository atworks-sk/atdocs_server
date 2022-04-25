package com.sk.atdocs.dto.project

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ProjectEntity

data class SearchDetailResDto(
    var id : Long?,
    val projectName: String?,
    var createDate: String?,
    var modifyDate: String?
//    var works :  ArrayList<SearchDetailWorkResDto>
){
    // 추가 생성자 (projectEntity)
    constructor(project : ProjectEntity) : this (
            project.id,
            project.projectName,
            DateUtils.convertLocalDateTimeToString(project.createdDateTime, "yyyy-MM-dd"),
            DateUtils.convertLocalDateTimeToString(project.modifiedDateTime, "yyyy-MM-dd")
//            ,getWorks(project.works)
    )
}
//fun getWorks( works : Collection<WorkEntity>?) : ArrayList<SearchDetailWorkResDto> {
//    var resList: ArrayList<SearchDetailWorkResDto> = ArrayList<SearchDetailWorkResDto>()
//    works?.map {
//        resList.add(SearchDetailWorkResDto(it))
//    }
//    return resList
//}
