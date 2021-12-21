package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_CLAZZ_FIELD")
class ClazzFieldEntity (
    snapshot:SnapshotEntity,
    clazz: ClazzEntity,
    accessSpecifier : String,
    name : String,   
    typeText : String,
    comment : String?,
    fullContents:String
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

    // 접근지정자
    var accessSpecifier : String = accessSpecifier
    
    // 필드명
    var name : String = name

    // type text
    @Column(length = 4000)
    var typeText : String = typeText

    // 주석
    @Column(length = 4000)
    var comment : String? = comment

    // 메서드 전체 내용
    @Lob
    var fullContents : String = fullContents



    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "clazz_field_id")
    @OrderBy("id ASC")
    val typeList: MutableList<ClazzFieldTypeEntity>? = ArrayList()

}