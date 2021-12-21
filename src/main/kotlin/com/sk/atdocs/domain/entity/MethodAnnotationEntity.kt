package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_ANNOTATION")
class MethodAnnotationEntity (
    snapshot:SnapshotEntity,
    method: MethodEntity,
    expression : String,
    annotationName : String,
    param1 : String?,
    param2 : String?
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


    // 전체 표현식
    @Column(length = 1000)
    val expression : String = expression

    // name
    @Column(length = 1000)
    val annotationName : String = annotationName

    // param1
    @Column(length = 1000)
    val param1 : String? = param1

    // param2
    @Column(length = 1000)
    val param2 : String? = param2

}