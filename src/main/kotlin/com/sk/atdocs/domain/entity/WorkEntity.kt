package com.sk.atdocs.domain.entity

import org.hibernate.annotations.Formula
import javax.persistence.*


/**
 * 최소 업무 단위
 */
@Entity
@Table(name = "TB_WORK")
class WorkEntity (workName : String, packagePath: String?, project : ProjectEntity ) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    @Column( length = 100 )
    var workName : String = workName

    @Column( length = 100 )
    var packagePath : String? = packagePath

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    val project : ProjectEntity = project

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "work_id")
    @OrderBy("id ASC")
    val works : Collection<SourceEntity>? = null

    var javaFileCnt : Long? = 0


    var xmlFileCnt : Long? = 0

}