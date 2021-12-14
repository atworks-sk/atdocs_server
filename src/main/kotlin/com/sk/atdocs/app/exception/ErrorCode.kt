package com.sk.atdocs.app.exception

enum class ErrorCode(
    var code : String,
    var message : String
) {
    ERROR("E001", "error.message.unknown"),
    ERROR_NOT_MODIFY_OBJECT("E002","데이터 저장에 실패했습니다."),
    ERROR_SEARCH_CODE_INFO("E003","코드정보를 조회하지 못했습니다.")
}