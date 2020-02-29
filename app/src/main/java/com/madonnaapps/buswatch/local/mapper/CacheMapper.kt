package com.madonnaapps.buswatch.local.mapper

interface CacheMapper<CacheObject, DomainObject> {

    fun mapFromCacheObject(cacheObject: CacheObject): DomainObject

    fun mapToCacheObject(domainObject: DomainObject): CacheObject

}