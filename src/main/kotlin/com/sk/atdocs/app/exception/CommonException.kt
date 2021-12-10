package com.sk.atdocs.app.exception

import com.sk.atdocs.app.util.DateUtils
import com.sk.atdocs.domain.entity.ProjectEntity
import org.springframework.http.HttpStatus

open class CommonException(
    val code: String,
     message: String,
    val status: HttpStatus,
) : RuntimeException(message){

    // 추가 생성자
    constructor(code: String, message: String) : this (
        code, message, HttpStatus.INTERNAL_SERVER_ERROR
    )

    // 추가 생성자
    constructor(errorCode: ErrorCode) : this (
        errorCode.code, errorCode.message, HttpStatus.INTERNAL_SERVER_ERROR
    )
}