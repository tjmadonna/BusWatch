package com.madonnaapps.buswatch.ui.common.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.madonnaapps.buswatch.R
import kotlin.math.min

class CircleTextView : AppCompatTextView {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    constructor(context: Context) : super(context) {
        setupView(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupView(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        setupView(attrs, defStyleAttr)
    }

    private fun setupView(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CircleTextView,
            defStyleAttr,
            0
        )
        circlePaint.color = typedArray.getColor(R.styleable.CircleTextView_circleColor, Color.BLACK)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(width / 2f, height / 2f, min(width, height) / 2f, circlePaint)
        super.onDraw(canvas)
    }
}