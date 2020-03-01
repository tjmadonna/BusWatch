package com.madonnaapps.buswatch.config.version

import com.madonnaapps.buswatch.data.version.StopFileVersion
import javax.inject.Inject

class StopJsonFileVersion @Inject constructor() : StopFileVersion {
    override val value: Int
        get() = 1
}