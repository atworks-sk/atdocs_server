package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_CALL")
class MethodCallEntity (
    snapshot:SnapshotEntity,
    method: MethodEntity,
    scope : String?,
    name : String,
    argumentCnt : Int
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

//    @Column(length = 999)
    var scope : String? = scope

//    @Column(length = 222)
    var name : String = name
    var argumentCnt : Int = argumentCnt

    // 호출 메서드 리스
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_call_id")
    @OrderBy("id ASC")
    val argList: MutableList<MethodCallArgEntity>? = ArrayList()


}