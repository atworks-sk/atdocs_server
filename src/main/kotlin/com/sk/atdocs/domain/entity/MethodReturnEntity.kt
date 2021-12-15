package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_RETURN")
class MethodReturnEntity (
    snapshot:SnapshotEntity,
    method:MethodEntity,
    elementName : String,
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
    @JoinColumn(name = "method_id")
    val method : MethodEntity = method

    var elementName : String = elementName

    var elementDepth : Long = elementDepth

    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_clazz_id")
    val elementClazz : ClazzEntity? = elementClazz


}