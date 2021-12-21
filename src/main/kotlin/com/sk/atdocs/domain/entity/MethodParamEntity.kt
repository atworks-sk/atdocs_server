package com.sk.atdocs.domain.entity

import javax.persistence.*


@Entity
@Table(name = "TB_METHOD_PARAM")
class MethodParamEntity (
    snapshot : SnapshotEntity,
    method : MethodEntity,
    name : String,
    typeText : String
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

    var name : String = name

    @Column(length = 1000)
    var typeText : String = typeText

    // 클래스에서 전역변수
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_param_id")
    @OrderBy("id ASC")
    val typeList : MutableList<MethodParamTypeEntity>? = ArrayList()

}