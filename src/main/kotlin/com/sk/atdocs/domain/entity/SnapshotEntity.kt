package com.sk.atdocs.domain.entity

import javax.persistence.*


@Entity
@Table(name = "TB_SNAPSHOT")
class SnapshotEntity (project: ProjectEntity) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // project 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private val project : ProjectEntity? = project

    // 클래스 리스트
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "snapshot_id")
    @OrderBy("id ASC")
    private val clazzList: Collection<ClazzEntity>? = null

    // 클래스 어노테이스 리스트


    // 메서드 리스트
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "snapshot_id")
    @OrderBy("id ASC")
    private val methodList: Collection<MethodEntity>? = null

}