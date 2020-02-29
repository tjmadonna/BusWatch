/*
 * Copyright 2017 Tyler Madonna
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

package com.madonnaapps.buswatch.splash

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.madonnaapps.buswatch.stops.StopsActivity
import javax.inject.Inject

internal class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.parseResult().observe(this, Observer { parseResult ->

            // Make sure parse results isn't null. This shouldn't happen
            if (parseResult == null) {
                return@Observer
            }

            // Log error if one exists
            val error = parseResult.error

            if (error != null) {
                Log.e("SplashActivity", error.message, error)
            }

            // Start stops activity and finish this activity to prevent being in back stack
            val intent = StopsActivity.createIntent(this)
            startActivity(intent)
            finish()

        })

    }

}
