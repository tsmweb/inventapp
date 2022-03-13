package br.com.tsmweb.inventapp.common.camera

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class FrameLayoutCaptureLayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : FrameLayout(context, attrs, style) {

    private lateinit var transparentPaint: Paint
    private lateinit var strokePaint: Paint

    private var color: Int = 0
    private val rect: RectF = RectF()
    private var parentWith: Int = 0
    private var parentHeight: Int = 0
    private val boxCornerRadius: Float = 10f

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        setWillNotDraw(false)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        transparentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }

        strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FFFFFFFF")
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }

        color = Color.parseColor("#CC000000")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        parentWith = MeasureSpec.getSize(widthMeasureSpec)
        parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        val rectWith = (parentWith * 0.9).toInt() / 2 // 90%
        val rectHeight = (parentHeight * 0.5).toInt() / 2 // 50%

        val top = (parentHeight / 2f) - rectHeight
        val bottom = (parentHeight / 2f) + rectHeight
        val left = (parentWith / 2f) - rectWith
        val right = (parentWith / 2f) + rectWith

        rect.set(left, top, right, bottom)

        setMeasuredDimension(parentWith, parentHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(color)
        canvas?.drawRoundRect(rect, boxCornerRadius, boxCornerRadius, transparentPaint)
        canvas?.drawRoundRect(rect, boxCornerRadius, boxCornerRadius, strokePaint)

        super.onDraw(canvas)
    }

}