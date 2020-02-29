package com.madonnaapps.buswatch.cache.mapper

interface CacheMapper<CacheObject, DomainObject> {

    fun mapFromCacheObject(cacheObject: CacheObject): DomainObject

    fun mapToCacheObject(domainObject: DomainObject): CacheObject

}