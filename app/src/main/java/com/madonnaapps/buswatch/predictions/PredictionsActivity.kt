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

import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.madonnaapps.buswatch.R
import kotlinx.android.synthetic.main.activity_predictions.*
import kotlinx.android.synthetic.main.content_predictions.*

import javax.inject.Inject

internal class PredictionsActivity : AppCompatActivity() {

    companion object {
        val EXTRA_STOP_CODE = "extra_stop_code"

        fun createIntent(context: Context, stopCode: Long): Intent {
            val intent = Intent(context, PredictionsActivity::class.java)
            intent.putExtra(EXTRA_STOP_CODE, stopCode)
            return intent
        }
    }

    @Inject
    lateinit var viewModel: PredictionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predictions)
        setSupportActionBar(predictions_toolbar)

        val adapter = PredictionsAdapter()
        val recyclerView = predictions_recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        viewModel.error.observe(this, Observer { error ->
            Log.d("PredictionsActivity", error)
        })

        viewModel.predictions.observe(this, Observer { predictions ->

            if (predictions == null) return@Observer

            adapter.items = predictions

        })

        viewModel.loadData(intent.extras!!.getLong(EXTRA_STOP_CODE, -1))

    }

}
