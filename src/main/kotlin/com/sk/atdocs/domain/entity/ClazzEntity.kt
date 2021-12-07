package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_CLAZZ")
class ClazzEntity (
    snapshot:SnapshotEntity,
    packageName: String,
    clazzName: String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // package Name
    var packageName :String =packageName

    // Clazz Name
    var clazzName :String = clazzName

    // Snapshot info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "clazz_id")
    @OrderBy("id ASC")
    private val methodList: Collection<MethodEntity>? = null
}