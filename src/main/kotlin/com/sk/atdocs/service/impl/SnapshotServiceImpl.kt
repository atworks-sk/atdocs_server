package com.sk.atdocs.service.implimport com.github.javaparser.StaticJavaParserimport com.github.javaparser.ast.CompilationUnitimport com.github.javaparser.ast.Nodeimport com.sk.atdocs.app.enum.CodeGroupimport com.sk.atdocs.app.exception.SnapshotErrorCodeimport com.sk.atdocs.domain.entity.*import com.sk.atdocs.domain.repository.SnapshotErrorRepositoryimport com.sk.atdocs.domain.repository.SnapshotRepositoryimport com.sk.atdocs.service.*import mu.KotlinLoggingimport org.springframework.stereotype.Serviceimport org.springframework.transaction.annotation.Transactionalimport java.io.Fileprivate val logger = KotlinLogging.logger {  }@Serviceclass SnapshotServiceImpl(    var snapshotRepository: SnapshotRepository,    var snapshotErrorRepository : SnapshotErrorRepository,    var projectService: ProjectService,    var clazzService: ClazzService,    var methodService: MethodService,    var codeService: CodeService):SnapshotService {    /*     * 자바 소스를 분석/번역 시스템     */    @Transactional(readOnly = false)    override fun CreateSnapshot(path: String, projectId: Long): Boolean {        // TODO 이내용을 커스터마이징으로 뺴야될것 같습니다.        val rootdir = File(path)        if(!rootdir.exists()){            logger.error { rootdir.absolutePath + " 존재하지 않습니다." }            return false        }        // Snapshot 생성합니다.        var project = projectService.findById(projectId)        var snapshot = snapshotRepository.save(SnapshotEntity(project))        logger.info { "프로젝트 명 : " + project.projectName }        logger.info { "Snapshot ID : " + snapshot.id }        logger.info { "클래스 정보를 등록합니다." }        getDirectory(rootdir, snapshot, "CLASS")        logger.info { "클래스 정보를 등록이 끝났습니다." }        logger.info { "매서드 정보를 등록합니다." }        getDirectory(rootdir, snapshot, "METHOD")        logger.info { "매서드 정보를 등록이 끝났습니다." }        return false    }    /*     * 스냅샷 등록시 오류건을 테이블에 기록합니다.     */    private fun updateSnapshotError(snapshot : SnapshotEntity, snapshotErrorCode : SnapshotErrorCode, filePath : String) {        if(snapshotErrorRepository.findBySnapshotAndFilePath(snapshot, filePath)?.isEmpty == true){            snapshotErrorRepository.save(                SnapshotErrorEntity(                    snapshot,                    snapshotErrorCode,                    filePath                )            )        }    }    /*     * directory search     */    fun getDirectory(dir:File, snapshot: SnapshotEntity, type : String) {        if( !dir.exists() || dir.isFile) {            return        }        var files = dir.listFiles()        if(files.isEmpty()){            return        }        files.forEach { file: File ->            if(file.isDirectory){                getDirectory(file, snapshot, type)            }            else {                val fileName = file.name                var clazzName = fileName.substring(0, fileName.lastIndexOf("."))                val ext = fileName.substring(fileName.lastIndexOf(".") + 1)                if( "java" != ext){                    // 동일한 SNAPSHOT, FILE 은 1번식만 들어가게 처리함                    updateSnapshotError(                        snapshot,                        SnapshotErrorCode.ERROR_NOT_JAVA_FILE,                        file.absolutePath                    )                    return                }                // TODO parsing exception -> com.github.javaparser.GeneratedJavaParser.generateParseException                val cu = StaticJavaParser.parse(file)                if(cu.packageDeclaration.isEmpty){                    updateSnapshotError(                        snapshot,                        SnapshotErrorCode.ERROR_FAIL_PARSE,                        file.absolutePath                    )                    return                }                if("CLASS" == type){                    setClazzInfo(cu, snapshot, clazzName, file.absolutePath  )                }                else if("METHOD" == type){                    // 등록된 Clazz 객체를 찾습니다.                    clazzService.searchClazzByFilePath(snapshot, file.absolutePath)?.let {                        // 등록된 클래스가 없는 경우 예외처리                        if(it.isEmpty){                            updateSnapshotError(                                snapshot,                                SnapshotErrorCode.ERROR_SEARCH_CLAZZ,                                file.absolutePath                            )                        }                        else{                            logger.info { "file.absolutePath -> " + file.absolutePath }                             setMethodInfo(cu, it.get())                        }                    }                }            }        }    }    /*     * Clazz 정보 등록     */    private fun setClazzInfo(        cu:CompilationUnit,        snapshot: SnapshotEntity,        clazzName : String,        fullPath : String    ) {        val packageName = cu.packageDeclaration?.get()?.nameAsString        var line = cu.range.get().end.line.toLong()        var clazz = clazzService.saveClazz(                ClazzEntity(snapshot, packageName!!, clazzName, line, fullPath)        )//        var annotationList: ArrayList<ClazzAnnotationEntity> =  ArrayList<ClazzAnnotationEntity>()        cu.types.forEach { type ->            // clazz annotation 정보 등록            type.annotations.forEach { annotation ->//                logger.info { "clazz -> " + clazz.id }                var param1 = if(annotation.childNodes.size > 1) annotation.childNodes[1].toString() else ""                var param2 = if(annotation.childNodes.size > 2) annotation.childNodes[2].toString() else ""                var annotationName = annotation.name.toString()                clazz.annotationList?.add(                    ClazzAnnotationEntity(                        clazz.snapshot,                        clazz,                        annotation.tokenRange.get().toString(),                        annotationName,                        param1,                        param2                    )                )            }        }        // TODO 이부분 추후 커스터마이징 영역으로 제외해야됩니다.        var controller = clazz.annotationList?.filter {            it.annotationName == "RestController" || it.annotationName == "Controller"        }        var repository =  clazz.annotationList?.filter {            it.annotationName == "Repository"        }        var entity =  clazz.annotationList?.filter {            it.annotationName == "Entity"        }        var data =  clazz.annotationList?.filter {            it.annotationName == "Data" || it.annotationName == "Getter"        }        if(!controller.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Controller")        }        else if(!repository.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Repository")        }        else if(!entity.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Entity")        }        else if(!data.isNullOrEmpty()){            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Data")        }//        clazzService.saveClazz(clazzNew!!)    }    /*     * 클래스 상세 정보 세팅     */    private fun setMethodInfo(cu: CompilationUnit, clazz: ClazzEntity) {        logger.info { "setMethodInfo call -> " + clazz.clazzName }        /*         * clazz import 정보 저장 ( import clazz 객체까지 연동됨 )         */        cu.imports.forEach { import ->            var clazzImportName = import.name?.toString()            clazzService.searchClazzByClazzFullName(clazz.snapshot, clazzImportName!!)?.let {                clazz.importList?.add(                    ClazzImportEntity(                        clazz.snapshot,                        clazz,                        clazzImportName,                        if(it.isEmpty) null else it.get()                    )                )            }        }        logger.info { "clazz import 정보 저장 완료" }        cu.types.forEach { type ->            // 클래스 전역 변수선언            type.fields.forEach{ filed ->                var accessSpecifier = filed.accessSpecifier.toString()                var comment = if(filed.comment.isEmpty) "" else filed.comment?.get()?.content?.toString()                var expression = filed.tokenRange?.get().toString() //                var filedName = filed.variables[0].name.toString()                var clazzfiledEntity = ClazzFiledEntity(                    clazz.snapshot,                    clazz,                    accessSpecifier,                    comment,                    expression,                    filedName                )                var type = filed.elementType                if(type.childNodes.size == 0){                    var clazzFiledElement =  ClazzFiledElementEntity(                        clazzfiledEntity.snapshot,                        clazzfiledEntity,                        filed.elementType.toString(),                        0,                        null                    )                    clazzfiledEntity.filedElementList?.add(clazzFiledElement)                }                else{                    setClazzFiledElement(clazzfiledEntity, type.childNodes, 0)                }                clazz.filedList?.add( clazzfiledEntity )            }            //Method 정보 등록            type.methods.forEach { method ->                var methodEntity = methodService.createMethod(                    MethodEntity(                        method.nameAsString,                        method.accessSpecifier.toString(),                        method.range.get().end.line.toLong()-method.range.get().begin.line.toLong(),                        method.toString(),                        clazz.snapshot,                        clazz                    )                )            }        }//        val fileName = file.name//        var clazzName = fileName.substring(0, fileName.lastIndexOf("."))        logger.info { "setMethodInfo call 종료"}    }    private fun setClazzFiledElement( clazzfiledEntity: ClazzFiledEntity, node: List<Node>, elementDepth: Long) {        var elementName = node[0].toString()        // import data 찾기        var clazzImportData = clazzfiledEntity.clazz.importList?.filter {            it.clazzName == elementName        }        var clazzData : ClazzEntity? = null        if(clazzImportData?.size == 1){            clazzData = clazzImportData[0].importClazz        }        else{            // package 내에서 찾아야 됨            var fullName = "${clazzfiledEntity.clazz.packageName}.$elementName"            clazzService.searchClazzByClazzFullName(clazzfiledEntity.snapshot, fullName)?.let {                clazzData = if(it.isEmpty) null else it.get()            }        }        var clazzFiledElement =  ClazzFiledElementEntity(            clazzfiledEntity.snapshot,            clazzfiledEntity,            elementName,            elementDepth,            clazzData        )        clazzfiledEntity.filedElementList?.add(clazzFiledElement)        if(node.size > 1){            setClazzFiledElement(clazzfiledEntity, node[1].childNodes, elementDepth+1)        }    }    /**************************************************************    class aTdocsVisitor: VoidVisitorAdapter<Void>() {        override fun visit(node: MethodDeclaration, aBoolean: Void?) {            println("Method declaration: ${node.nameAsString}")            return super.visit(node, aBoolean)        }        override fun visit(node: IntersectionType, aBoolean: Void?){            println("Intersection: ${node.elements}")            return super.visit(node, aBoolean)        }        override fun visit(node: FieldDeclaration, aBoolean: Void?) {            println("FieldDeclaration: ${node.elementType} ${node.variables[0].name}")            return super.visit(node, aBoolean)        }        override fun visit(node: MethodCallExpr, aBoolean: Void?){            println("Method call: ${node.scope.get()} > ${node.nameAsString}")    //        println("${node}")    // item.getOfficialCountries()            val resolved = node.resolve()            resolved?.let { println("${it.declaringType().qualifiedName}::${it.name}") }    //        JavaParserFacade.get()            return super.visit(node, aBoolean)        }        override fun visit(node: MethodReferenceExpr?, aBoolean: Void?) {            print("Method Reference: $node")            return super.visit(node, aBoolean)        }    }     **************************************************************/}