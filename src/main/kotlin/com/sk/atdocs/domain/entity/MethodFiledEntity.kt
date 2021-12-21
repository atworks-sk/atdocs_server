package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_FILED")
class MethodFiledEntity (
    snapshot:SnapshotEntity,
    method: MethodEntity,
    name : String,
    typeText : String,
    initializer : String?
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


    @Column(length = 1000)
    var name : String = name

    @Column(length = 1000)
    var typeText : String = typeText

    @Lob
    var initializer : String? = initializer

    // 자식 Method
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_filed_id")
    @OrderBy("id ASC")
    val typeList: MutableList<MethodFiledTypeEntity>? = ArrayList()

}