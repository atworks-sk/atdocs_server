package com.sk.atdocs.service.impl

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.stmt.SwitchStmt
import com.github.javaparser.ast.visitor.GenericVisitorAdapter
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import com.sk.atdocs.domain.entity.ClazzAnnotationEntity
import com.sk.atdocs.domain.entity.ClazzEntity
import com.sk.atdocs.domain.entity.MethodEntity
import com.sk.atdocs.domain.entity.SnapshotEntity
import com.sk.atdocs.domain.repository.MethodRepository
import com.sk.atdocs.domain.repository.SnapshotRepository
import com.sk.atdocs.service.ClazzService
import com.sk.atdocs.service.MethodService
import com.sk.atdocs.service.ProjectService
import com.sk.atdocs.service.SnapshotService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File


private val logger = KotlinLogging.logger {  }
@Service
class SnapshotServiceImpl(
    var snapshotRepository: SnapshotRepository,
    var projectService: ProjectService,
    var clazzService: ClazzService,
    var methodService: MethodService
):SnapshotService {

    /*
     * 자바 소스를 분석/번역 시스템
     */
    override fun CreateSnapshot(path: String, projectId: Long): Boolean {
        logger.info { "Project Id : " + projectId }
        val rootdir = File(path)
        if(!rootdir.exists()){
            logger.error { rootdir.absolutePath + " 존재하지 않습니다." }
        }
        else if(!rootdir.isDirectory){
            logger.error { rootdir.absolutePath + " 는 directory가 아닙니다." }
        }

        // Snapshot 생성합니다.
        var project = projectService.findById(projectId)
        logger.info { "프로젝트 명 : " + project.projectName }

        var snapshot = snapshotRepository.save(SnapshotEntity(project))
        getDirectory(rootdir, snapshot)
        return false
    }

    /*
     * directory search
     */
    fun getDirectory(dir:File, snapshot: SnapshotEntity) {

        if( !dir.exists() || dir.isFile) {
            return
        }
        var files = dir.listFiles()
        if(files.isEmpty()){
            return
        }

        files.forEach { file: File ->
            if(file.isDirectory){
                getDirectory(file, snapshot)
            }
            else{
                setClazzInfo(file, snapshot)
            }
        }
    }

    /*
     * Clazz 정보 등록
     */
    private fun setClazzInfo(file:File, snapshot: SnapshotEntity) {

        val fileName = file.name
        var clazzName = fileName.substring(0, fileName.lastIndexOf("."))
        val ext = fileName.substring(fileName.lastIndexOf(".") + 1)

        if(!"java".equals(ext)){
            TODO("애러 건으로 처리")
            return
        }
        val cu = StaticJavaParser.parse(file)
        val packageName = cu.packageDeclaration.orElseThrow { RuntimeException() }

        // 클래스 등록
        var clazz = clazzService.saveClazz(ClazzEntity(snapshot, packageName.toString(), clazzName))

        // 클래스 상세 정보 세팅
        this.setClazzDetailInfo(cu, clazz)
    }

    /*
     * 클래스 상세 정보 세팅
     */
    public fun setClazzDetailInfo(cu: CompilationUnit, clazz: ClazzEntity) {
        cu.types.forEach { type ->

            //Method 정보 등록
            type.methods.forEach { method ->
                var methodEntity = methodService.createMethod(
                    MethodEntity(
                        method.nameAsString,
                        method.accessSpecifier.toString(),
                        method.toString(),
                        clazz.snapshot,
                        clazz
                    )
                );

                // method parameter 정보
                method.parameters.forEach{parameter ->
                    logger.info { "파라메터 " +  parameter.getNameAsString() }
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
