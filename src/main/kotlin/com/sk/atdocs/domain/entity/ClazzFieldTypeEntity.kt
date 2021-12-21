package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_CLAZZ_FIELD_TYPE")
class ClazzFieldTypeEntity (
    snapshot:SnapshotEntity,
    clazzFiled : ClazzFieldEntity?,
    clazzName : String,
    packageName : String?,
    myClazz : ClazzEntity?,
    parent : ClazzFieldTypeEntity?
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
    @JoinColumn(name = "clazz_field_id")
    val clazzFiled : ClazzFieldEntity? = clazzFiled

    var clazzName : String = clazzName

    var packageName : String? = packageName

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_clazz_id")
    var myClazz : ClazzEntity? = myClazz

    // 상위 ClazzFieldTypeEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent : ClazzFieldTypeEntity? = parent

    // 자식 ClazzFieldTypeEntity
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "parent_id")
    @OrderBy("id ASC")
    val children: MutableList<ClazzFieldTypeEntity>? = ArrayList()


}