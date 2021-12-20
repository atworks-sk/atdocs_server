package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_PARAM_ELEMENT")
class MethodParamElementEntity (
    snapshot : SnapshotEntity,
    methodParam : MethodParamEntity,
    elementClazzName : String,
    elementPackageName : String?,
    elementDepth : Long,
    elementClazz : ClazzEntity?
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // 클래스 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_param_id")
    val methodParam : MethodParamEntity = methodParam

    var elementClazzName : String = elementClazzName
    var elementPackageName : String? = elementPackageName
    var elementDepth : Long = elementDepth

    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_clazz_id")
    val elementClazz : ClazzEntity? = elementClazz


}