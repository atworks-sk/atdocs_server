package com.sk.atdocs.service

interface SnapshotService {
    //
    fun CreateSnapshot(path: String, projectId: Long) : Boolean
}