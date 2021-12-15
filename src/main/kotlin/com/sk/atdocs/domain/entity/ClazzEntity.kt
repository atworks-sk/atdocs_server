package com.sk.atdocs.domain.entity
import javax.persistence.*
import javax.persistence.criteria.CriteriaBuilder.Case




@Entity
@Table(name = "TB_CLAZZ")
class ClazzEntity (
    snapshot:SnapshotEntity,
    packageName: String,
    clazzName: String,
    line : Long,
    filePath : String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // package Name
    var packageName :String =packageName

    // Clazz Name
    var clazzName :String = clazzName

    // package + clazz
    var clazzFullName : String = "$packageName.$clazzName"

    // Clazz size
    var line : Long = line

    // 등록시 사용된 full path
    var filePath : String = filePath

    @ManyToOne
    var clazzTypeCd: CodeEntity? = null

    // Snapshot info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot


    // MethodEntity List
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "clazz_id")
    @OrderBy("id ASC")
    val methodList: MutableList<MethodEntity>? = ArrayList()

    // 클래스 어노테이션 리스트
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "clazz_id")
    @OrderBy("id ASC")
    val annotationList: MutableList<ClazzAnnotationEntity>? = ArrayList()

    // 클래스에서 임포트한 타 클래스
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "clazz_id")
    @OrderBy("id ASC")
    val importList: MutableList<ClazzImportEntity>? =  ArrayList()

    // 타 클래스에서 현재 클래스를 임포트한 정보
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "import_clazz_id")
    @OrderBy("id ASC")
    val importClazzList: MutableList<ClazzImportEntity>? = ArrayList()


    // 클래스에서 전역변수
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "clazz_id")
    @OrderBy("id ASC")
    val filedList: MutableList<ClazzFiledEntity>? = ArrayList()


}