package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD_FIELD_TYPE")
class MethodFieldTypeEntity (
    snapshot:SnapshotEntity,
    methodFiled :MethodFieldEntity?, // 최상위만 MethodFiledEntity 를 가지고 있습니다.
    clazzName : String,
    packageName : String?,
    myClazz : ClazzEntity?,
    parent : MethodFieldTypeEntity?
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // 메서드 필드 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_filed_id")
    val methodFiled : MethodFieldEntity? = methodFiled

    var clazzName : String = clazzName

    var packageName : String? = packageName

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_clazz_id")
    var myClazz : ClazzEntity? = myClazz


    // 상위 MethodFiledTypeEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent : MethodFieldTypeEntity? = parent

    // 자식 MethodFiledTypeEntity
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "parent_id")
    @OrderBy("id ASC")
    val children: MutableList<MethodFieldTypeEntity>? = ArrayList()

}