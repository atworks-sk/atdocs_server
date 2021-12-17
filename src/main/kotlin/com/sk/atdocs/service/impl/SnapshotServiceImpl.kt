package com.sk.atdocs.service.implimport com.github.javaparser.StaticJavaParserimport com.github.javaparser.ast.CompilationUnitimport com.github.javaparser.ast.Nodeimport com.sk.atdocs.app.enum.CodeGroupimport com.sk.atdocs.app.exception.SnapshotErrorCodeimport com.sk.atdocs.domain.entity.*import com.sk.atdocs.domain.repository.SnapshotErrorRepositoryimport com.sk.atdocs.domain.repository.SnapshotRepositoryimport com.sk.atdocs.dto.snapshot.SearchListReqDtoimport com.sk.atdocs.dto.snapshot.SnapshotDtoimport com.sk.atdocs.service.*import mu.KotlinLoggingimport org.springframework.data.domain.Pageimport org.springframework.data.domain.Pageableimport org.springframework.data.jpa.domain.Specificationimport org.springframework.stereotype.Serviceimport org.springframework.transaction.annotation.Transactionalimport java.io.Fileprivate val logger = KotlinLogging.logger {  }@Serviceclass SnapshotServiceImpl(    var snapshotRepository: SnapshotRepository,    var snapshotErrorRepository : SnapshotErrorRepository,    var projectService: ProjectService,    var clazzService: ClazzService,    var codeService: CodeService):SnapshotService {    /*     * snapshot list search     */    @Transactional(readOnly = false)    override fun searchList(reqDto: SearchListReqDto, pageable: Pageable): Page<SnapshotDto>? {        // project id equal        fun equalProjectId(projectId: Long?): Specification<SnapshotEntity> {            return Specification<SnapshotEntity> { root, query, builder ->                projectId?.let {                    builder.equal(root.get<Any>("project")!!.get<Any>("id"), it)                }                        ?: null            }        }        var entities = snapshotRepository.findAll(equalProjectId(reqDto.projectId)                , pageable)        return entities.map { snapshotEntity: SnapshotEntity? ->            SnapshotDto(snapshotEntity!!)        }    }    @Transactional(readOnly = false)    override fun deleteSnapshot(id: Long) {        snapshotRepository.delete(snapshotRepository.findById(id).get())    }    /*     * 자바 소스를 분석/번역 시스템     */    @Transactional(readOnly = false)    override fun CreateSnapshot(path: String, projectId: Long): Boolean {        // TODO 이내용을 커스터마이징으로 뺴야될것 같습니다.        val rootDir = File(path)        if(!rootDir.exists())            return false        // Snapshot 생성합니다.        var project = projectService.findById(projectId)        var snapshot = snapshotRepository.save(SnapshotEntity(project, path))        logger.info { "프로젝트 명 : " + project.projectName }        logger.info { "Snapshot ID : " + snapshot.id }        logger.info { "클래스 정보를 등록합니다." }        getDirectory(rootDir, snapshot, "CLASS")        logger.info { "클래스 정보를 등록이 끝났습니다." }        logger.info { "매서드 정보를 등록합니다." }        getDirectory(rootDir, snapshot, "METHOD")        logger.info { "매서드 정보를 등록이 끝났습니다." }        return false    }    /*     * directory search     */    fun getDirectory(dir:File, snapshot: SnapshotEntity, type : String) {        if( (!dir.exists() || dir.isFile) && dir.listFiles().isEmpty()) {            return        }        dir.listFiles().forEach { file: File ->            if(file.isDirectory){                getDirectory(file, snapshot, type)            }            else {                val fileName = file.name                var clazzName = fileName.substring(0, fileName.lastIndexOf("."))                val ext = fileName.substring(fileName.lastIndexOf(".") + 1)                if( "java" != ext){                    // 동일한 SNAPSHOT, FILE 은 1번식만 들어가게 처리함                    updateSnapshotError(snapshot, SnapshotErrorCode.ERROR_NOT_JAVA_FILE, file.absolutePath)                    return                }                var cu : CompilationUnit? = null                try{                    cu = StaticJavaParser.parse(file)                    if(cu.packageDeclaration.isEmpty){                        updateSnapshotError(snapshot, SnapshotErrorCode.ERROR_FAIL_PARSE, file.absolutePath)                        return                    }                }                catch(e : RuntimeException){                    updateSnapshotError(snapshot, SnapshotErrorCode.ERROR_FAIL_PARSE, file.absolutePath)                    return                }                if("CLASS" == type){                    setClazzInfo(cu!!, snapshot, clazzName, file.absolutePath  )                }                else if("METHOD" == type){                    // 등록된 Clazz 객체를 찾습니다.                    clazzService.searchClazzByFilePath(snapshot, file.absolutePath)?.let {                        // 등록된 클래스가 없는 경우 예외처리                        if(it.isEmpty){                            updateSnapshotError(snapshot, SnapshotErrorCode.ERROR_SEARCH_CLAZZ,file.absolutePath)                            return                        }                        setMethodInfo(cu, it.get())                    }                }                else if("CALL" == type){                }            }        }    }    /*     * Clazz 정보 등록     */    private fun setClazzInfo(        cu:CompilationUnit,        snapshot: SnapshotEntity,        clazzName : String,        fullPath : String    ) {        val packageName = cu.packageDeclaration?.get()?.nameAsString        var line = cu.range.get().end.line.toLong()        var comment = if(cu.types[0].comment.isEmpty) "" else cu.types[0].comment?.get()?.content?.toString()        var clazz =  clazzService.saveClazz(                ClazzEntity(snapshot, packageName!!, clazzName, line, fullPath, comment)        )        var isInterface : Boolean = false        cu.types.forEach { type ->            logger.info { "type.isClassOrInterfaceDeclaration -> " + type.isClassOrInterfaceDeclaration }            isInterface = type.isClassOrInterfaceDeclaration            // clazz annotation 정보 등록            type.annotations.forEach { annotation ->                var param1 = if(annotation.childNodes.size > 1) annotation.childNodes[1].toString() else ""                var param2 = if(annotation.childNodes.size > 2) annotation.childNodes[2].toString() else ""                var annotationName = annotation.name.toString()                clazz.annotationList?.add(                    ClazzAnnotationEntity(                        clazz.snapshot,                        clazz,                        annotation.tokenRange.get().toString(),                        annotationName,                        param1,                        param2                    )                )            }        }        if(isInterface){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Interface")            return        }        // TODO 이부분 추후 커스터마이징 영역으로 제외해야됩니다.        var controller = clazz.annotationList?.filter {            it.annotationName == "RestController" || it.annotationName == "Controller"        }        var repository =  clazz.annotationList?.filter {            it.annotationName == "Repository"        }        var entity =  clazz.annotationList?.filter {            it.annotationName == "Entity"        }        var data =  clazz.annotationList?.filter {            it.annotationName == "Data" || it.annotationName == "Getter"        }        if(!controller.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Controller")        }        else if(!repository.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Repository")        }        else if(!entity.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Entity")        }        else if(!data.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Data")        }    }    /*     * 클래스 상세 정보 세팅     */    private fun setMethodInfo(cu: CompilationUnit, clazz: ClazzEntity) {        /*         * clazz import 정보 저장 ( import clazz 객체까지 연동됨 )         */        cu.imports.forEach { import ->            var clazzImportName = import.name?.toString()            clazzService.searchClazzByClazzFullName(clazz.snapshot, clazzImportName!!)?.let {                clazz.importList?.add(                    ClazzImportEntity(                        clazz.snapshot,                        clazz,                        clazzImportName,                        if(it.isEmpty) null else it.get()                    )                )            }        }        cu.types.forEach { type ->            // 클래스 전역 변수선언            type.fields.forEach{ filed ->                var accessSpecifier = filed.accessSpecifier.toString()                var comment = if(filed.comment.isEmpty) "" else filed.comment?.get()?.content?.toString()                var expression = filed.tokenRange?.get().toString() //                var filedName = filed.variables[0].name.toString()                var clazzFiled = ClazzFiledEntity(                    clazz.snapshot,                    clazz,                    accessSpecifier,                    comment,                    expression,                    filedName                )                var type = filed.elementType                // Clazz Filed Element List Save                if(type.childNodes.size == 0){                    clazzFiled.filedElementList?.add(                        ClazzFiledElementEntity(                            clazzFiled.snapshot,                            clazzFiled,                            type.toString(),                            0,                            null                        )                    )                }                else{                    setClazzFiledElement(clazzFiled, type.childNodes, 0)                }                // clazz filed list save                clazz.filedList?.add( clazzFiled )            }            //Method 정보 등록            type.methods.forEach { method ->                var comment = if(method.comment.isEmpty) "" else method.comment?.get()?.content?.toString()                var methodEntity = MethodEntity(                        method.nameAsString,                        method.accessSpecifier.toString(),                        method.type.toString(),                        method.range.get().end.line.toLong()-method.range.get().begin.line.toLong(),                        method.toString(),                        comment,                        clazz.snapshot,                        clazz                )                // Method Return List Setting                if(method.type.childNodes.size < 1){                    methodEntity.methodReturnList?.add(                        MethodReturnEntity(                            methodEntity.snapshot,                            methodEntity,                            method.type.toString(),                            0,                            null                        )                    )                }                else{                    setMethodReturnElement(methodEntity, method.type.childNodes, 0)                }                // method parameter setting                method.parameters.forEach {                    var comment = if(it.comment.isEmpty) "" else it.comment?.get()?.content?.toString()                    var methodParamEntity = MethodParamEntity(                        methodEntity.snapshot,                        methodEntity,                        it.type.toString(),                        it.name.toString(),                        comment                    )                    // method parameter element setting                    if(it.type.childNodes.size < 1){                        methodParamEntity.methodParamElementList?.add(                            MethodParamElementEntity(                                methodParamEntity.snapshot,                                methodParamEntity,                                it.type.toString(),                                0,                                null                            )                        )                    }                    else{                        setMethodParamElement(methodParamEntity, it.type.childNodes, 0)                    }                    methodEntity.methodParamList?.add(methodParamEntity)                }                clazz.methodList?.add( methodEntity )            }        }    }    private fun getElementClazz (clazz: ClazzEntity, elementName : String) : ClazzEntity?{        var clazzImportData =  clazz.importList?.filter {            it.clazzName == elementName        }        var clazzData : ClazzEntity? = null        if(clazzImportData?.size == 1){            return clazzImportData[0].importClazz        }        else{            // package 내에서 찾아야 됨            var fullName = "${clazz.packageName}.$elementName"            clazzService.searchClazzByClazzFullName(clazz.snapshot, fullName)?.let {                return if(it.isEmpty) null else it.get()            }        }        return null    }    private fun setMethodParamElement (methodParamEntity : MethodParamEntity, node: List<Node>, elementDepth: Long) {        var elementName = node[0].toString()        // import data 찾기        var clazzData : ClazzEntity? = getElementClazz(methodParamEntity.method.clazz, elementName)        methodParamEntity.methodParamElementList?.add(            MethodParamElementEntity(                methodParamEntity.snapshot,                methodParamEntity,                elementName,                elementDepth,                clazzData            )        )        if(node.size > 1){            if(node[1].childNodes?.size == 0){                methodParamEntity.methodParamElementList?.add(                    MethodParamElementEntity(                        methodParamEntity.snapshot,                        methodParamEntity,                        node[1].toString(),                        elementDepth+1,                        null                    )                )                return            }            setMethodParamElement(methodParamEntity, node[1].childNodes, elementDepth+1)        }    }    private fun setMethodReturnElement (methodEntity : MethodEntity, node: List<Node>, elementDepth: Long) {        var elementName = node[0].toString()        var clazzData : ClazzEntity? = getElementClazz(methodEntity.clazz, elementName)        var methodReturnEntity = MethodReturnEntity(            methodEntity.snapshot,            methodEntity,            elementName,            elementDepth,            clazzData        )        methodEntity.methodReturnList?.add(methodReturnEntity)        if(node.size > 1){            if(node[1].childNodes?.size == 0){                methodEntity.methodReturnList?.add(                    MethodReturnEntity(                        methodEntity.snapshot,                        methodEntity,                        node[1].toString(),                        elementDepth+1,                        null                    )                )                return            }            setMethodReturnElement(methodEntity, node[1].childNodes, elementDepth+1)        }    }    /*     * Clazz Filed Element Set Function     */    private fun setClazzFiledElement( clazzFiledEntity: ClazzFiledEntity, node: List<Node>, elementDepth: Long) {        var elementName = node[0].toString()        // import data 찾기        var clazzData : ClazzEntity? = getElementClazz(clazzFiledEntity.clazz, elementName)        var clazzFiledElement =  ClazzFiledElementEntity(            clazzFiledEntity.snapshot,            clazzFiledEntity,            elementName,            elementDepth,            clazzData        )        clazzFiledEntity.filedElementList?.add(clazzFiledElement)        if(node.size > 1){            if(node[1].childNodes?.size == 0){                clazzFiledEntity.filedElementList?.add(                    ClazzFiledElementEntity(                        clazzFiledEntity.snapshot,                        clazzFiledEntity,                        node[1].toString(),                        elementDepth+1,                        null                    )                )                return            }            setClazzFiledElement(clazzFiledEntity, node[1].childNodes, elementDepth+1)        }    }    /*     * 스냅샷 등록시 오류건을 테이블에 기록합니다.     */    private fun updateSnapshotError(snapshot : SnapshotEntity, snapshotErrorCode : SnapshotErrorCode, filePath : String) {        if(snapshotErrorRepository.findBySnapshotAndFilePath(snapshot, filePath)?.isEmpty == true){            snapshot.snapshotErrorList?.add(                SnapshotErrorEntity( snapshot, snapshotErrorCode, filePath )            )        }    }}