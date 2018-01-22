/*
 * Copyright 2018 Tyler Madonna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.madonnaapps.buswatch.predictions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import android.util.Log
import com.madonnaapps.buswatch.data.PredictionsRepository
import com.madonnaapps.buswatch.data.remote.TrueTimeApiPrediction
import com.madonnaapps.buswatch.data.remote.TrueTimeApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class PredictionsViewModel constructor(
        private val predictionsRepository: PredictionsRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val predictions = MutableLiveData<List<TrueTimeApiPrediction>>()

    val error = MutableLiveData<String>()

    private var call: Call<TrueTimeApiResponse>? = null
        set(value) {
            call?.cancel()
            field = value
        }

    fun loadData(stopCode: Long) {

        val isLoading = loading.value ?: false

        if (isLoading) {
            return
        }

        loading.value = true

        AsyncTask.execute {

            call = predictionsRepository.predictions(stopCode)

            call!!.enqueue(object : Callback<TrueTimeApiResponse> {

                override fun onResponse(retrofitCall: Call<TrueTimeApiResponse>?,
                                        response: Response<TrueTimeApiResponse>?) {

                    if (response == null) {
                        createError("No response returned")
                        return
                    }

                    if (!response.isSuccessful) {
                        createError(response.message())
                        return
                    }

                    val dataResponse = response.body()

                    if (dataResponse == null) {
                        createError("No data returned")
                        return
                    }

                    val result = dataResponse.result

                    if (result.error != null) {
                        createError(result.error.message)
                        return
                    }

                    val data = result.predictions

                    if (data == null || data.isEmpty()) {
                        createError("No predictions returned")
                        return
                    }

                    predictions.value = data

                    loading.value = false

                }

                override fun onFailure(call: Call<TrueTimeApiResponse>?, t: Throwable?) {
                    createError(t?.localizedMessage)
                }

            })

        }

    }

    private fun createError(errorMessage: String?) {
        loading.value = false
        error.value = errorMessage ?: "An unknown error occurred"
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("PredictionsViewModel", "onClear called")
        call = null
    }

}