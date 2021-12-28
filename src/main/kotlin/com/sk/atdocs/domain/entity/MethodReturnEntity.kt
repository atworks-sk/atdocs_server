package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_RETURN")
class MethodReturnEntity (
    snapshot : SnapshotEntity,
    method : MethodEntity?,
    clazzName : String,
    packageName : String?,
    myClazz : ClazzEntity?,
    parent : MethodReturnEntity?
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // method_id 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id")
    val method : MethodEntity? = method


    var clazzName : String = clazzName

    var packageName : String? = packageName

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_clazz_id")
    var myClazz : ClazzEntity? = myClazz

    // 상위 MethodFiledTypeEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent : MethodReturnEntity? = parent

    // 자식 MethodFiledTypeEntity
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "parent_id")
    @OrderBy("id ASC")
    val children: MutableList<MethodReturnEntity>? = ArrayList()

}