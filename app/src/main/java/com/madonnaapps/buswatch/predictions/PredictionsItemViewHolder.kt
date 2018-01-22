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

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.madonnaapps.buswatch.R
import kotlin.properties.Delegates

internal class PredictionsItemViewHolder(itemView: View, val predictionsItemViewModel: PredictionsItemViewModel) : RecyclerView.ViewHolder(itemView) {

    private var routeTextView: TextView by Delegates.notNull()

    private var destinationTextView: TextView by Delegates.notNull()

    private var arrivalTimeTextView: TextView by Delegates.notNull()

    init {
        routeTextView = itemView.findViewById(R.id.predictions_list_item_route)
        destinationTextView = itemView.findViewById(R.id.predictions_list_item_destination)
        arrivalTimeTextView = itemView.findViewById(R.id.predictions_list_item_arrival_time)
    }

    fun bindView() {

        routeTextView.text = predictionsItemViewModel.route
        destinationTextView.text = predictionsItemViewModel.destination

        val arrivalTime = predictionsItemViewModel.arrivalTime?.toInt()
        arrivalTimeTextView.text = arrivalTimeText(arrivalTime)

    }

    private fun arrivalTimeText(time: Int?): String =

        when {

            time == null -> "Couldn't load arrival time"

            time >= 2 -> "Arriving in " + time + " minutes"

            time == 1 -> "Arriving in 1 minute"

            time == 0 -> "Arriving now"

            time == -1 -> "Left 1 minute ago"

            else -> "Left " + Math.abs(time) + " minutes ago"

    }

}