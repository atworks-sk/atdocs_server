package com.sk.atdocs.service

import com.sk.atdocs.dto.sample.SampleDto

interface SampleService {
    fun sampleSelect():List<SampleDto>?
    fun sampleInsert(name: String) : Boolean
}