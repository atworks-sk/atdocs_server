package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_CLAZZ_ANNOTATION")
class ClazzAnnotationEntity (
    snapshot:SnapshotEntity,
    clazz: ClazzEntity,
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
    @JoinColumn(name = "clazz_id")
    val clazz : ClazzEntity = clazz

    // 전체 표현식
    @Column(length = 100)
    val expression : String = expression

    // name
    @Column(length = 100)
    val annotationName : String = annotationName


    // param1
    @Column(length = 100)
    val param1 : String? = param1
    // param2
    @Column(length = 100)
    val param2 : String? = param2

}