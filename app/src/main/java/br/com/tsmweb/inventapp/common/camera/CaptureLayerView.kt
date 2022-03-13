package br.com.tsmweb.inventapp.common.camera

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import br.com.tsmweb.inventapp.R

class CaptureLayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : View(context, attrs, style) {

    private var bitmap: Bitmap? = null

    private lateinit var paint: Paint
    private lateinit var strokePaint: Paint
    private lateinit var transparentPaint: Paint
    private lateinit var semiTransparentPaint: Paint

    private val rect: RectF = RectF()
    private var parentWith: Int = 0
    private var parentHeight: Int = 0
    private val boxCornerRadius: Float = resources.getDimension(R.dimen.capture_box_corner_radius)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, R.color.capture_box_stroke_color)
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }

        transparentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }

        semiTransparentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, R.color.capture_background_color)
        }
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

        bitmap = Bitmap.createBitmap(parentWith, parentHeight, Bitmap.Config.ARGB_8888)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Canvas(bitmap!!).apply {
            // semi-transparent background
            drawRect(0F, 0F, width.toFloat(), height.toFloat(), semiTransparentPaint)
            // transparent rectangle
            drawRoundRect(rect, boxCornerRadius, boxCornerRadius, transparentPaint)
            // rectangle border
            drawRoundRect(rect, boxCornerRadius, boxCornerRadius, strokePaint)
        }

        canvas?.drawBitmap(bitmap!!, 0F, 0F, paint)
    }

}