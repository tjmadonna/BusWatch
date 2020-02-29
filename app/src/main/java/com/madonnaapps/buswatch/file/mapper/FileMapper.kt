package com.madonnaapps.buswatch.file.mapper

interface FileMapper<FileObject, DomainObject> {

    fun mapFromFileObject(fileObject: FileObject): DomainObject

}