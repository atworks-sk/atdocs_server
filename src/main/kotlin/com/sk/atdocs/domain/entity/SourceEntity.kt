package com.sk.atdocs.domain.entity

import com.sk.atdocs.domain.enum.SourceType
import javax.persistence.*


/**
 * 최소 업무 단위
 */
@Entity
@Table(name = "TB_SOURCE")
class SourceEntity (work : WorkEntity, fileName : String, sourceType : SourceType, lines: Int, sourceText : String) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    /*
     * java, xml
     */
//    @ManyToOne
//    var sourceCode : CodeEntity? = sourceCode
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    var sourceType: SourceType? = sourceType

    @Column( length = 100 )
    var fileName : String? = fileName

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    val work : WorkEntity = work

    @Column()
    val lines : Int = lines


    @Lob
    @Column(name = "source_text")
    val sourceText: String? = sourceText

}