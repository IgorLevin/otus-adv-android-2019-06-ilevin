package ru.igor.levin.weatherforecast

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator

class ProgressView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var rotationDirection: Int = 0
    private var center: PointF = PointF(0f, 0f)
    private var radius: Int = 5
    private var circleRadius = 100f
    private var beamWidth = 10f
    private var beamLength = 20f
    private var beamGap = 10f
    private var numberOfBeams = 8
    private var phase = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 12f
    }

    private val paintBeam = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private lateinit var animator: ValueAnimator

    private val onDownListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }

    private val detector: GestureDetector = GestureDetector(context, onDownListener)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0).apply {
            try {
                rotationDirection = getInteger(R.styleable.ProgressView_rotationDirection, 0)
            } finally {
                recycle()
            }
        }

        animator = ValueAnimator.ofFloat(0f, 359f).apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = 10000
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                phase = it.animatedValue as Float
                invalidate()
            }
        }
    }

    fun startProgress() {
        animator.start()
    }

    fun stopProgress() {
        animator.cancel()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event).let {result ->
            if (result) {
                startProgress()
                true
            } else
                false
        }
    }

    //    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//
//        center = PointF((w/2).toFloat(), (h/2).toFloat())
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {

            val cx = width/2f
            val cy = height/2f

            val beamAngle = 360f/numberOfBeams

            drawCircle(cx, cy, circleRadius, paint)

            rotate(phase, cx, cy)
            for(i in 0 until numberOfBeams) {
                drawRect(
                    cx - beamWidth / 2,
                    cy - circleRadius - beamGap - beamLength,
                    cx + beamWidth / 2,
                    cy - circleRadius - beamGap,
                    paintBeam
                )
                rotate(beamAngle, cx, cy)
            }
        }
    }
}