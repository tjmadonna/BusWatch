package com.madonnaapps.buswatch.remote.mapper

interface RemoteMapper<RemoteObject, DomainObject> {

    fun mapFromRemoteObject(remoteObject: RemoteObject): DomainObject

}