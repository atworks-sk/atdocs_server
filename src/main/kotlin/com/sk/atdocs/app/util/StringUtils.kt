package com.sk.atdocs.app.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
 * LocalDate, LocalDateTime 관련 함수
 */
class StringUtils {

    companion object {

        /*
         * LocalDateTime 를 String 형태로 변환 (공통 포맷 yyyy-MM-dd kk:mm)
         */
        fun getFilePath (filePath : String?, separatorChar : String) : String {
            return try {
                filePath!!.substring(0, filePath.lastIndexOf(separatorChar))
            }
            catch (e: Exception){
                ""
            }
        }

        fun getFileName (filePath : String?, separatorChar : String) : String {
            return try {
                filePath!!.substring(filePath.lastIndexOf(separatorChar)+1, filePath.length)
            }
            catch (e: Exception){
                ""
            }
        }
    }

}