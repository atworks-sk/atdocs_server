package com.sk.atdocs.domain.entity

import com.sk.atdocs.app.exception.SnapshotErrorCode
import javax.persistence.*


@Entity
@Table(name = "TB_SNAPSHOT_ERROR")
class SnapshotErrorEntity (
    snapshot : SnapshotEntity,
    errorCode : SnapshotErrorCode,
    filePath : String?
    ) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    // Snapshot info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id")
    val snapshot : SnapshotEntity = snapshot

    // error code
    var code : String? = errorCode!!.code
    var message : String? = errorCode!!.message


    @Column(length = 1000)
    var filePath : String? = filePath
}