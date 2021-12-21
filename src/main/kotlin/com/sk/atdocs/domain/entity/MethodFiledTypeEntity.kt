package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_FILED_TYPE")
class MethodFiledTypeEntity (
    snapshot:SnapshotEntity,
    methodFiled :MethodFiledEntity?, // 최상위만 MethodFiledEntity 를 가지고 있습니다.
    name : String,
    packagePath : String?,
    myClazz : ClazzEntity?,
    parent : MethodFiledTypeEntity?
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot


    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_filed_id")
    val methodFiled : MethodFiledEntity? = methodFiled

    var clazzName : String = name

    var packagePath : String? = packagePath

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_clazz_id")
    var myClazz : ClazzEntity? = myClazz


    // 상위 MethodFiledTypeEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent : MethodFiledTypeEntity? = parent

    // 자식 MethodFiledTypeEntity
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "parent_id")
    @OrderBy("id ASC")
    val children: MutableList<MethodFiledTypeEntity>? = ArrayList()

}