package com.sk.atdocs.domain.enum

enum class SourceType(
    var value : String
) {
    XML("xml"),
    JAVA("java");

    companion object {
        private val lookup = HashMap<String, SourceType>()

        init {
            SourceType.values().map { lookup.put(it.value, it) }
        }

        fun getFromValue(value: String): SourceType {
            return lookup[value]!!
        }
    }
}