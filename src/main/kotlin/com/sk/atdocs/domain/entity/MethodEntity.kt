package com.sk.atdocs.domain.entity
import org.hibernate.annotations.Type
import javax.persistence.*

@Entity
@Table(name = "TB_METHOD")
class MethodEntity (
    methodName:String,
    accessSpecifier:String,
    returnText : String,
    line : Long,
    fullContents:String,
    comment:String?,
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

    var returnText : String = returnText

    var line : Long = line

    // 메서드 전체 내용
    @Lob @Type(type = "org.hibernate.type.TextType")
    var fullContents : String = fullContents

    @Lob @Type(type = "org.hibernate.type.TextType")
    var comment : String? = comment

    // Snapshot info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // Class Info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    val clazz : ClazzEntity = clazz

    // 매서드 리턴 데이터
    @OneToOne(mappedBy = "method", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var methodReturn : MethodReturnEntity? = null

    // 매서드 리턴 데이터
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_id")
    @OrderBy("id ASC")
    val methodParamList: MutableList<MethodParamEntity>? = ArrayList()



    // 매서드 어노테이션
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_id")
    @OrderBy("id ASC")
    val annotationList: MutableList<MethodAnnotationEntity>? = ArrayList()

    // 메서드 필드 리스트
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_id")
    @OrderBy("id ASC")
    val filedList: MutableList<MethodFieldEntity>? = ArrayList()


    // 호출 메서드 리스
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "method_id")
    @OrderBy("id ASC")
    val callList: MutableList<MethodCallEntity>? = ArrayList()


    @OneToOne(mappedBy = "method", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var restApi : RestApiEntity? = null



}