package com.sk.atdocs.service.impl

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import com.sk.atdocs.app.enum.CodeGroup
import com.sk.atdocs.app.exception.SnapshotErrorCode
import com.sk.atdocs.domain.entity.*
import com.sk.atdocs.domain.repository.SnapshotErrorRepository
import com.sk.atdocs.domain.repository.SnapshotRepository
import com.sk.atdocs.service.*
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File


private val logger = KotlinLogging.logger {  }
@Service
class SnapshotServiceImpl(
    var snapshotRepository: SnapshotRepository,
    var snapshotErrorRepository : SnapshotErrorRepository,
    var projectService: ProjectService,
    var clazzService: ClazzService,
    var methodService: MethodService,
    var codeService: CodeService
):SnapshotService {

    /*
     * 자바 소스를 분석/번역 시스템
     */
    override fun CreateSnapshot(path: String, projectId: Long): Boolean {

        // TODO 이내용을 커스터마이징으로 뺴야될것 같습니다.
        val rootdir = File(path)
        if(!rootdir.exists()){
            logger.error { rootdir.absolutePath + " 존재하지 않습니다." }
        }


        // Snapshot 생성합니다.
        var project = projectService.findById(projectId)
        logger.info { "프로젝트 명 : " + project.projectName }

        var snapshot = snapshotRepository.save(SnapshotEntity(project))
        getDirectory(rootdir, snapshot, "CLASS")
        getDirectory(rootdir, snapshot, "METHOD")
        return false
    }


    private fun updateSnapshotError(snapshot : SnapshotEntity, snapshotErrorCode : SnapshotErrorCode, filePath : String) {

        if(snapshotErrorRepository.findBySnapshotAndFilePath(snapshot, filePath)?.isEmpty == true){
            snapshotErrorRepository.save(
                SnapshotErrorEntity(
                    snapshot,
                    snapshotErrorCode,
                    filePath
                )
            )
        }



    }

    /*
     * directory search
     */
    fun getDirectory(dir:File, snapshot: SnapshotEntity, type : String) {

        if( !dir.exists() || dir.isFile) {
            return
        }
        var files = dir.listFiles()
        if(files.isEmpty()){
            return
        }

        files.forEach { file: File ->
            if(file.isDirectory){
                getDirectory(file, snapshot, type)
            }
            else {

                val fileName = file.name
                var clazzName = fileName.substring(0, fileName.lastIndexOf("."))
                val ext = fileName.substring(fileName.lastIndexOf(".") + 1)

                if( "java" != ext){
                    // 동일한 SNAPSHOT, FILE 은 1번식만 들어가게 처리함
                    updateSnapshotError(
                        snapshot,
                        SnapshotErrorCode.ERROR_NOT_JAVA_FILE,
                        file.absolutePath
                    )

                    return
                }

                val cu = StaticJavaParser.parse(file)
                if(cu.packageDeclaration.isEmpty){
                    updateSnapshotError(
                        snapshot,
                        SnapshotErrorCode.ERROR_FAIL_PARSE,
                        file.absolutePath
                    )
                    return
                }
                if("CLASS" == type){
                    setClazzInfo(cu, snapshot, clazzName)
                }
                else if("METHOD" == type){

//                    setMethodInfo(cu, clazz)

//                setMethoInfo(file, snapshot)
                }
            }
        }
    }

    /*
     * Clazz 정보 등록
     */
    private fun setClazzInfo(cu:CompilationUnit, snapshot: SnapshotEntity, clazzName : String) {

        val packageName = cu.packageDeclaration?.get()?.nameAsString
        var line = cu.range.get().end.line.toLong()

        var clazz = clazzService.saveClazz(
                ClazzEntity(snapshot, packageName!!, clazzName, line )
        )

        var annotationList: ArrayList<ClazzAnnotationEntity> =  ArrayList<ClazzAnnotationEntity>()
        cu.types.forEach { type ->
            // clazz annotation 정보 등록
            type.annotations.forEach { annotation ->
                var param1 = if(annotation.childNodes.size > 1) annotation.childNodes.get(1).toString() else ""
                var param2 = if(annotation.childNodes.size > 2) annotation.childNodes.get(2).toString() else ""
                var annotationName = annotation.name.toString()
                annotationList.add(
                    clazzService.saveClazzAnnotation(
                        ClazzAnnotationEntity(
                                clazz.snapshot,
                                clazz,
                                annotation.tokenRange.get().toString(),
                                annotationName,
                                param1,
                                param2
                        )
                    )
                )
            }
        }


        // TODO 이부분 추후 커스터마이징 영역으로 제외해야됩니다.
        var controller = annotationList.filter {
            it.annotationName == "RestController" || it.annotationName == "Controller"
        }
        var repository =  annotationList.filter {
            it.annotationName == "Repository"
        }
        var entity =  annotationList.filter {
            it.annotationName == "Entity"
        }
        var data =  annotationList.filter {
            it.annotationName == "Data" || it.annotationName == "Getter"
        }

        if(!controller.isNullOrEmpty()){
            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Controller")
        }
        else if(!repository.isNullOrEmpty()){
            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Repository")
        }
        else if(!entity.isNullOrEmpty()){
            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Entity")
        }
        else if(!data.isNullOrEmpty()){
            clazz.clazzTypeCd = codeService.findByCode(CodeGroup.CLAZZ_TYPE.value, "Data")
        }

        clazzService.saveClazz(clazz)
    }

    /*
     * 클래스 상세 정보 세팅
     */
    private fun setMethodInfo(cu: CompilationUnit, clazz: ClazzEntity) {
//        val fileName = file.name
//        var clazzName = fileName.substring(0, fileName.lastIndexOf("."))

    }
    fun setClazzDetailInfo(cu: CompilationUnit, clazz: ClazzEntity) {
        cu.types.forEach { type ->

            //Method 정보 등록
            type.methods.forEach { method ->
                var methodEntity = methodService.createMethod(
                    MethodEntity(
                        method.nameAsString,
                        method.accessSpecifier.toString(),
                        method.range.get().end.line.toLong()-method.range.get().begin.line.toLong(),
                        method.toString(),
                        clazz.snapshot,
                        clazz
                    )
                )

                // method parameter 정보
                method.parameters.forEach{parameter ->

//                    methodService.createMethodParam(
//                        MethodParamEntity(
//                            parameter.typeAsString,
//                            parameter.nameAsString,
//                            clazz.snapshot,
//                            methodEntity
//                        )
//                    )
                }


            }

            // clazz annotation 정보 등록
            type.annotations.forEach { annotation ->
                // TODO 어노테이션에 파라메터가 2개이상인건에 대한 처리
                // 일단 단순하게 1개 테이블에 최대 2개까지 데이터를 입력할 수 있게 합니다.

                var param1 = if(annotation.childNodes.size > 1) annotation.childNodes.get(1).toString() else ""
                var param2 = if(annotation.childNodes.size > 2) annotation.childNodes.get(2).toString() else ""
                clazzService.saveClazzAnnotation(
                    ClazzAnnotationEntity(
                        clazz.snapshot,
                        clazz,
                        annotation.tokenRange.get().toString(),
                        annotation.name.toString(),
                        param1,
                        param2
                    )
                )
            }


            // 클래스 전역 변수선언
            type.fields.forEach{ filed ->

                logger.info { filed.toString() }

            }

        }

    }


    /**************************************************************
    class aTdocsVisitor: VoidVisitorAdapter<Void>() {

        override fun visit(node: MethodDeclaration, aBoolean: Void?) {
            println("Method declaration: ${node.nameAsString}")
            return super.visit(node, aBoolean)
        }
        override fun visit(node: IntersectionType, aBoolean: Void?){
            println("Intersection: ${node.elements}")
            return super.visit(node, aBoolean)
        }

        override fun visit(node: FieldDeclaration, aBoolean: Void?) {
            println("FieldDeclaration: ${node.elementType} ${node.variables[0].name}")
            return super.visit(node, aBoolean)
        }

        override fun visit(node: MethodCallExpr, aBoolean: Void?){
            println("Method call: ${node.scope.get()} > ${node.nameAsString}")
    //        println("${node}")    // item.getOfficialCountries()
            val resolved = node.resolve()
            resolved?.let { println("${it.declaringType().qualifiedName}::${it.name}") }
    //        JavaParserFacade.get()
            return super.visit(node, aBoolean)
        }

        override fun visit(node: MethodReferenceExpr?, aBoolean: Void?) {
            print("Method Reference: $node")
            return super.visit(node, aBoolean)
        }
    }
     **************************************************************/
}
