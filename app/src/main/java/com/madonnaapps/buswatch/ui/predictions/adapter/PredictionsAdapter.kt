package com.madonnaapps.buswatch.ui.predictions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.ui.predictions.model.ArriveTime.*
import com.madonnaapps.buswatch.ui.predictions.model.PredictionItemViewModel
import kotlinx.android.synthetic.main.list_item_predictions.view.*

class PredictionsAdapter : RecyclerView.Adapter<PredictionsAdapter.ViewHolder>() {

    private val differ =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<PredictionItemViewModel>() {

            override fun areItemsTheSame(
                oldItem: PredictionItemViewModel,
                newItem: PredictionItemViewModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PredictionItemViewModel,
                newItem: PredictionItemViewModel
            ): Boolean {
                return oldItem.timeToArrival == newItem.timeToArrival &&
                        oldItem.route == newItem.route &&
                        oldItem.destination == newItem.destination
            }
        })

    fun submitPredictions(
        predictions: List<PredictionItemViewModel>,
        completeCallback: (() -> Unit)?
    ) {
        differ.submitList(predictions, completeCallback)
    }

    // PredictionsAdapter methods

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_predictions, parent, false)
        )
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    // View Holder

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @ExperimentalStdlibApi
        fun bind(item: PredictionItemViewModel) = with(itemView) {
            text_predictions_item_route.text = item.route
            text_predictions_item_destination.text = item.destination
            text_predictions_item_time.text = when (item.timeToArrival) {
                is NowArriveTime -> context.resources.getString(R.string.arrival_time_now)
                is OneMinuteArriveTime -> context.resources.getString(R.string.arrival_time_one, 1)
                is ManyMinutesArriveTime -> context.resources.getString(
                    R.string.arrival_time_many,
                    item.timeToArrival.minutes
                )
            }
        }
    }
}