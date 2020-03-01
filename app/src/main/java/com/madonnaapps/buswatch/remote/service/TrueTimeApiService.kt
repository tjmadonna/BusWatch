package com.madonnaapps.buswatch.remote.service

import com.madonnaapps.buswatch.remote.model.prediction.RemotePredictionResponse
import io.reactivex.Single
import retrofit2.http.Query

interface TrueTimeApiService {

    fun getPredictions(@Query("stpid") stopCode: String): Single<RemotePredictionResponse>

}