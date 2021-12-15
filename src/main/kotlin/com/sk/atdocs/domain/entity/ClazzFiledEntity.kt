package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_CLAZZ_FILED")
class ClazzFiledEntity (
    snapshot:SnapshotEntity,
    clazz: ClazzEntity,
    accessSpecifier : String,
    comment : String?,
    expression : String,
    filedName : String
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

    var accessSpecifier : String = accessSpecifier

    @Column(length = 4000)
    var comment : String? = comment

    @Column(length = 2000)
    var expression : String = expression

    @Column(length = 1000)
    var filedName : String = filedName


    // 클래스에서 전역변수
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "clazz_filed_id")
    @OrderBy("id ASC")
    val filedElementList: MutableList<ClazzFiledElementEntity>? = ArrayList()

}