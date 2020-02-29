package com.madonnaapps.buswatch.data.datastore.stop

import com.madonnaapps.buswatch.domain.model.Stop
import io.reactivex.Single

interface StopFileDataStore {

    fun getStops(): Single<List<Stop>>

}