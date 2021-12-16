package com.sk.atdocs.domain.entity

import javax.persistence.*


@Entity
@Table(name = "TB_METHOD_PARAM")
class MethodParamEntity (
    snapshot : SnapshotEntity,
    method : MethodEntity,
    elementText : String,
    filedName : String,
    comment : String?
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // Snapshot info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // Class Info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id")
    val method : MethodEntity = method

    @Column(length = 1000)
    var elementText : String = elementText

    @Column(length = 1000)
    var filedName : String = filedName

    @Column(length = 4000)
    var comment : String? = comment

    // 클래스에서 전역변수
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_param_id")
    @OrderBy("id ASC")
    val methodParamElementList : MutableList<MethodParamElementEntity>? = ArrayList()

}