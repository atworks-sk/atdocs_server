package com.sk.atdocs.service

import com.sk.atdocs.dto.snapshot.SearchListReqDto
import com.sk.atdocs.dto.snapshot.SnapshotDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SnapshotService {

    // snapshot list search
    fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<SnapshotDto>?

    // snapshot 삭제
    fun deleteSnapshot(id: Long)


    // snapshot 등록
    fun CreateSnapshot(path: String, projectId: Long) : Boolean

}