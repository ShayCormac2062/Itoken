package com.example.itoken.features.addtoken.presentation.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.example.itoken.App
import kotlin.math.abs

class PaintView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mX: Float = 0f
    private var mY: Float = 0f
    private var mPath: Path? = null
    private var mPaint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = DEFAULT_COLOR
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        xfermode = null
        alpha = 0xff
    }
    private val paths: ArrayList<FingerPath> = ArrayList()
    private var currentColor: Int = 0
    private var strokeWidth: Int = 0
    private var emboss: Boolean = false
    private var blur: Boolean = false
    private var mEmboss: MaskFilter = EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f)
    private var mBlur: MaskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)
    var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mBitmapPaint: Paint = Paint(Paint.DITHER_FLAG)

    fun init() {
        mBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        mCanvas = mBitmap?.let {Canvas(it)}
        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE
    }

    fun normal() {
        emboss = false
        blur = false
    }

    fun sizeNormal() {
        strokeWidth = 10
    }

    fun sizeBig() {
        strokeWidth = 15
    }

    fun sizeSmall() {
        strokeWidth = 5
    }

    fun colorGreen() {
        currentColor = Color.GREEN
    }

    fun colorYellow() {
        currentColor = Color.YELLOW
    }

    fun colorBlue() {
        currentColor = Color.BLUE
    }

    fun colorRed() {
        currentColor = Color.RED
    }

    fun colorCyan() {
        currentColor = Color.CYAN
    }

    fun colorWhite() {
        currentColor = Color.WHITE
    }

    fun colorBlack() {
        currentColor = Color.BLACK
    }

    fun emboss() {
        emboss = true
        blur = false
    }

    fun blur() {
        emboss = false
        blur = true
    }

    fun clear() {
        paths.clear()
        normal()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas?.drawColor(Color.WHITE)
        for ((color, emboss1, blur1, strokeWidth1, path) in paths) {
            mPaint.color = color
            mPaint.strokeWidth = strokeWidth1.toFloat()
            mPaint.maskFilter = null
            if (emboss1) mPaint.maskFilter = mEmboss else if (blur1) mPaint.maskFilter = mBlur
            mCanvas?.drawPath(path, mPaint)
        }
        mBitmap?.let { canvas.drawBitmap(it, 0f, 0f, mBitmapPaint) }
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val fp = mPath?.let { FingerPath(currentColor, emboss, blur, strokeWidth, it) }
        fp?.let { paths.add(it) }
        mPath?.reset()
        mPath?.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath?.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    companion object {
        var BRUSH_SIZE = 20
        const val DEFAULT_COLOR = Color.BLACK
        const val DEFAULT_BG_COLOR = Color.WHITE
        private const val TOUCH_TOLERANCE = 1f
    }

}