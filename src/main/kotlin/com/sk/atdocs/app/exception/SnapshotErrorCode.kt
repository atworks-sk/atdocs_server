package com.sk.atdocs.app.exception

enum class SnapshotErrorCode(
    var code : String,
    var message : String
) {
    ERROR_NOT_JAVA_FILE("S001", "JAVA 파일이 아닙니다."),
    ERROR_FAIL_PARSE("S002","JAVA Parsing 도중 애러가 발생하였습니다."),
    ERROR_SEARCH_CLAZZ("S003","상위 Class 정보를 찾지 못했습니다.")
}