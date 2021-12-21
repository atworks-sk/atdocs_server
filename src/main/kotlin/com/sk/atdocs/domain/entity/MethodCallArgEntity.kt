package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_CALL_ARG")
class MethodCallArgEntity (
    snapshot:SnapshotEntity,
    methodCall: MethodCallEntity,
    name : String?
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
    @JoinColumn(name = "method_call_id")
    val methodCall : MethodCallEntity = methodCall

    @Column(length = 1000)
    var name : String? = name

}