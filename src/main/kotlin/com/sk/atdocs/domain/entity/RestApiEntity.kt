package com.sk.atdocs.domain.entity

import org.springframework.http.HttpMethod
import javax.persistence.*


@Entity
@Table(name = "TB_REST_API")
class RestApiEntity (
    snapshot:SnapshotEntity,
    method: MethodEntity,
    urlPath : String,
    httpMethod: HttpMethod
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // 스냅샷 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // 클래스 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id")
    val method : MethodEntity = method

    // 전체 표현식
    @Column(length = 1000)
    val urlPath : String = urlPath


    @Column(name = "http_method")
    @Enumerated(EnumType.STRING)
    val httpMethod: HttpMethod? = httpMethod

}