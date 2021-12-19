package com.sk.atdocs.domain.entity
import javax.persistence.*


/*
 * 클래스 InheritanceType (implements, extends)
 */

@Entity
@Table(name = "TB_INHERITANCE_CLAZZ")
class ClazzInheritanceEntity (
    snapshot:SnapshotEntity,
    clazz: ClazzEntity,
    clazzName : String,
    packageName : String,
    inheritanceClazz : ClazzEntity?
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

    // package Name
    var packageName :String =packageName

    // Clazz Name
    var clazzName :String = clazzName

    // 상속한 클래스 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inheritance_clazz_id")
    val inheritanceClazz : ClazzEntity? = inheritanceClazz
}