package com.sk.atdocs.domain.entity

import javax.persistence.*


@Entity
@Table(name = "TB_METHOD_PARAM")
class MethodArgsEntity (

    argumentType : String,
    snapshot : SnapshotEntity,
    method : MethodEntity
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


    // parameter : 메서드 인자
    // return    : 메서드 리턴 데이터
    var argType : String = argumentType

    // 인자의 패키지
    var argClazzPackage : String = "패키지명"

    // 인자의 클래스명
    var argClazzName : String = "클래스명"

    // 인자의 클래스 ID ( 2차 프로세스에 등록됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arg_clazz_id")
    val argClazz : ClazzEntity? = null

    // 0 인경우 Root 아니면 하위 목록
    var argDepth : Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private val parent: MethodArgsEntity? = null

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private val children: Collection<MethodArgsEntity>? = null


}