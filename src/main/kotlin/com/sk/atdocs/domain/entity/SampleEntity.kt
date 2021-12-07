package com.sk.atdocs.domain.entity
import javax.persistence.*

@Entity
@Table(name = "TB_SAMPLE")
class SampleEntity (name: String) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null
    var name :String =name

}