package com.tehron.shoh.customVizualSeekBar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.properties.Delegates

class CustomSeekBarView(
    context: Context,
    private val attributeSet: AttributeSet?,
    private val defStyleAttrs: Int,
    private val defStyleRes: Int
) : View(context, attributeSet, defStyleAttrs) {
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttrs: Int) : this(
        context,
        attributeSet,
        defStyleAttrs,
        R.style.DefaultCustomSeekBarViewStyle
    )

    constructor(context: Context, attributeSet: AttributeSet?) : this(
        context,
        attributeSet,
        R.attr.customSeekBarViewStyle
    )

    constructor(context: Context) : this(context, null)

    var columns = listOf<CustomSeekBarViewColumn>()
        set(value) {
            field = value
            invalidate()
        }

    var onProgressChangedListener: OnProgressChangedListener? = null

    private var columnsColor = DEFAULT_COLUMNS_COLOR
    private var columnPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var rectF = RectF(0f, 16f, 0f, 0f)
    private var h by Delegates.notNull<Int>()
    private var w by Delegates.notNull<Int>()

    init {
        initializeAttributes()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            widthMeasureSpec,
            heightMeasureSpec
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w
        this.h = h
    }

    private var columnWidth: Float = 0f

    override fun onDraw(canvas: Canvas) {
        columnWidth = w / columns.size.toFloat()
        for (i in columns.indices) {
            if ((columnWidth * i + paddingLeft) > (measuredWidth - paddingRight))
                break
            rectF.bottom = h - h * (columns[i].columnHeight / 100)
            rectF.top = 16f + h * (columns[i].columnHeight / 100)
            rectF.left = columnWidth * i + paddingLeft
            rectF.right = columnWidth * i + 8f + paddingLeft
            columnPaint.color =
                if (columns[i].columnState == ColumnStatus.PLAYED) Color.GREEN else Color.BLUE
            canvas.drawRoundRect(
                rectF,
                16f,
                16f,
                columnPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                val eventX = event.x - paddingLeft
                val columnNumber = (eventX / columnWidth).toInt()
                onProgressChangedListener?.invoke(columnNumber)
                return performClick()
            }
        }

        return false
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun initializeAttributes() {
        if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.CustomSeekBarView,
                defStyleAttrs,
                defStyleRes
            )

            columnsColor = typedArray.getColor(
                R.styleable.CustomSeekBarView_columnsColor,
                DEFAULT_COLUMNS_COLOR
            )
            columnPaint.color = columnsColor
            columnPaint.style = Paint.Style.FILL

            typedArray.recycle()
        }
    }

    companion object {
        private const val DEFAULT_COLUMNS_COLOR = Color.BLUE
    }

}