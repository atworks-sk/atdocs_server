package com.sk.atdocs.service.impl

import com.sk.atdocs.domain.entity.SampleEntity
import com.sk.atdocs.domain.repository.SampleRepository
import com.sk.atdocs.dto.sample.SampleDto
import com.sk.atdocs.service.SampleService

import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {  }
@Service
class SampleServiceImpl(
    var sampleRepository: SampleRepository
):SampleService {
    override fun sampleSelect(): List<SampleDto>? {
        var list = sampleRepository.findAllBy()

//        list.forEach {sample->logger.info { sample.name }}
        return sampleRepository.findAll().map { obj -> convert(sampleEntity = obj) }
    }

    fun convert(sampleEntity : SampleEntity) : SampleDto {
        return SampleDto(
            id = sampleEntity.id,
            name = sampleEntity.name
        )
    }

//    fun convert


    override fun sampleInsert(name: String) : Boolean {
        var sampleEntity =  SampleEntity(name=name);
        var result: SampleEntity? = sampleRepository.save(sampleEntity)

        if(result != null){
            return true
        }
        return false
    }


}