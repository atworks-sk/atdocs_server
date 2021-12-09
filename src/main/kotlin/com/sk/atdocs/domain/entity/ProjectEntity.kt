package com.sk.atdocs.domain.entity

import javax.persistence.*
import javax.persistence.criteria.CriteriaBuilder.Case


@Entity
@Table(name = "TB_PROJECT")
class ProjectEntity (projectName : String, packageName : String?) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    @Column( unique=true )
    var projectName : String = projectName

    var packageName : String? = packageName

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "project_id")
    @OrderBy("id ASC")
    val snapshotList: Collection<SnapshotEntity>? = null

}