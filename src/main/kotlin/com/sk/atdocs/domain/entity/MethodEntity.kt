package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD")
class MethodEntity (
    methodName:String,
    accessSpecifier:String,
    line : Long,
    fullContents:String,
    snapshot:SnapshotEntity,
    clazz:ClazzEntity

) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    //메서드 명
    var methodName : String = methodName

    // 접근 제어자 (public, private, protect)
    var accessSpecifier : String = accessSpecifier

    var line : Long = line

    // 메서드 전체 내용
    @Lob
    var fullContents : String = fullContents

    // Snapshot info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // Class Info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    val clazz : ClazzEntity = clazz
}