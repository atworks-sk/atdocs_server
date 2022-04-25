package com.sk.atdocs.service.impl

import com.sk.atdocs.app.exception.CommonException
import com.sk.atdocs.app.exception.ErrorCode
import com.sk.atdocs.domain.entity.SourceEntity
import com.sk.atdocs.domain.entity.WorkEntity
import com.sk.atdocs.domain.enum.SourceType
import com.sk.atdocs.domain.repository.SourceRepository
import com.sk.atdocs.domain.repository.WorkRepository
import com.sk.atdocs.dto.source.AddSourceReqDto
import com.sk.atdocs.dto.source.SearchSourcesResDto
import com.sk.atdocs.dto.source.SearchSourcesTextResDto
import com.sk.atdocs.service.CodeService
import com.sk.atdocs.service.SourceService
import com.sk.atdocs.service.WorkService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileReader
import java.io.LineNumberReader
import java.util.*


private val logger = KotlinLogging.logger {  }

@Service
class SourceServiceImpl(
        var workService: WorkService,
        var workRepository : WorkRepository,
        var sourceRepository: SourceRepository,
        var codeService: CodeService

        ): SourceService {

    @Transactional()
    override fun addSource(requestDto: AddSourceReqDto) {

        // TODO 기존 데이터 삭제 또는 Snapshot 해야되는데 Snapshot 의미 있을까?
        var rootPath : String = requestDto.sourcePath;
        val rootDir = File(rootPath)
        if(!rootDir.exists())
            throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)

        var workOptional: Optional<WorkEntity> = workRepository.findById(requestDto.workId)
        if(workOptional.isPresent){

            var work : WorkEntity = workOptional.get()
            saveSource(rootDir, work)
            // java, xml 파일 갯수 저장
            work.javaFileCnt  = sourceRepository.countByWorkIdAndAndSourceType(work.id!!, SourceType.JAVA)
            work.xmlFileCnt  = sourceRepository.countByWorkIdAndAndSourceType(work.id!!, SourceType.XML)

            workService.save(work)

        }
        else{
            throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
        }


    }



    fun saveSource(dir: File, work: WorkEntity) {

        // empty directory
        if(!dir.exists() || ( dir.isDirectory && dir.listFiles().isEmpty())){
            return
        }

        dir.listFiles().forEach { file : File ->
            if(file.isDirectory) saveSource(file, work)
            else{
                val fileName = file.name
                val ext = fileName.substring(fileName.lastIndexOf(".") + 1)

                val lineNumberReader = LineNumberReader(FileReader(file))
                lineNumberReader.skip(Long.MAX_VALUE)
                val lines = lineNumberReader.lineNumber
                if( SourceType.XML.value == ext || SourceType.JAVA.value == ext){

                    // TODO 파일이 엄청 큰경우에는 경로만 저장합니다.
                    sourceRepository.save(
                            SourceEntity(
                                    work
                                    , fileName
                                    , SourceType.getFromValue(ext)
                                    , lines
                                    , file.readText(Charsets.UTF_8)
                            )
                    )
                }
            }
        }
    }

    override fun searchSource(workId: Long, sourceName: String, sourceType: String, pageable: Pageable): Page<SearchSourcesResDto>? {

        if(sourceType == ""){
            var sources = sourceRepository.findByWorkIdAndFileNameLike(workId, "%$sourceName%",  pageable);
            return sources.map { it ->
                SearchSourcesResDto(it!!)
            }
        }
        else{
            var sourceTypeObj : SourceType = SourceType.getFromValue(sourceType);
            var sources = sourceRepository.findByWorkIdAndFileNameLikeAndSourceType(workId, "%$sourceName%", sourceTypeObj, pageable);
            return sources.map { it ->
                SearchSourcesResDto(it!!)
            }
        }
    }

    override fun searchSourceText(workId: Long): SearchSourcesTextResDto? {
        var source: Optional<SourceEntity> = sourceRepository.findById(workId)
        if(source.isPresent){
            return SearchSourcesTextResDto(source.get())
        }
        throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
    }

    override fun delete(sourceId: Long) {
       sourceRepository.findByIdOrNull(sourceId)?.let {
           sourceRepository.delete(it)
            return;
        }
        ?: throw CommonException(ErrorCode.ERROR_NOT_MODIFY_OBJECT)
    }

}