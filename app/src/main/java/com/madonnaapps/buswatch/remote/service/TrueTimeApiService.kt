package com.madonnaapps.buswatch.remote.service

import com.madonnaapps.buswatch.BuildConfig
import com.madonnaapps.buswatch.remote.model.prediction.RemotePredictionResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TrueTimeApiService {

    @GET(BuildConfig.TRUE_TIME_API_GET_PREDICTIONS_PATH)
    fun getPredictions(@Query("stpid") stopCode: String): Single<RemotePredictionResponse>

}