package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_CLAZZ_IMPORT")
class ClazzImportEntity (
    snapshot:SnapshotEntity,
    clazz: ClazzEntity,
    clazzImportName : String,
    importClazz : ClazzEntity?
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

    // 클래스 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "import_clazz_id")
    val importClazz : ClazzEntity? = importClazz



    // 전체 표현식
    @Column(length = 400)
    val clazzImportName : String = clazzImportName



}