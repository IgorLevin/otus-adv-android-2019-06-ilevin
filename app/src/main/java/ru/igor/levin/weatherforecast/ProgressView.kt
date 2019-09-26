package ru.igor.levin.weatherforecast

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.CycleInterpolator
import timber.log.Timber
import kotlin.math.max

const val DEFAULT_WIDTH: Int = 300
const val DEFAULT_HEIGHT: Int = 300

class ProgressView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    private var rotationDirection: Int = 0
    private var center: PointF = PointF(0f, 0f)
    private var radius: Int = 5
    private var circleRadius = 100f
    private var beamWidth = 10f
    private var beamLength = 30f
    private var beamGap = 10f
    private var numberOfBeams = 8
    private var phase = 0f
    private var scale = 1f
    private var beams: ArrayList<RectF> = ArrayList()
    private val rotationMatrix: Matrix = Matrix()
    private val path = Path()

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
    private lateinit var animator2: ValueAnimator

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

        animator2 = ValueAnimator.ofFloat(1f, 20f).apply {
            //interpolator = AnticipateInterpolator()
            //interpolator = AccelerateInterpolator()
            //interpolator = BounceInterpolator()
            //interpolator = CycleInterpolator(2f)
            interpolator = AccelerateDecelerateInterpolator()

            duration = 5000
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                scale = it.animatedValue as Float
                invalidate()
            }
        }
    }

    fun startProgress() {
//        animator.start()
        animator2.start()
    }

    fun stopProgress() {
///        animator.cancel()
        animator2.cancel()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Timber.d("onTouchEvent: %s", event.toString())

        return detector.onTouchEvent(event).let {result ->
            if (result) {
                if (animator.isStarted) {
                    stopProgress()
                } else {
                    startProgress()
                }
                true
            } else
                false
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Timber.d("onAttachedToWindow")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        Timber.d("onFinishInflate")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Timber.d("onConfigurationChanged: %s", newConfig?.toString())
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        Timber.d("onApplyWindowInsets: %s", insets?.toString())
        return super.onApplyWindowInsets(insets)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Timber.d("onDetachedFromWindow")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Timber.d("onWindowVisibilityChanged: %d", visibility)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Timber.d("onLayout: %s, %d, %d, %d, %d", changed, left, top, right, bottom)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        Timber.d("onSizeChanged: %d, %d, %d, %d", w, h, oldw, oldh)

        val cx = w/2f
        val cy = h/2f

        val beamRect = RectF(
            cx - beamWidth / 2,
            cy - circleRadius - beamGap - beamLength,
            cx + beamWidth / 2,
            cy - circleRadius - beamGap
        )

        val beamAngle = 360f/numberOfBeams

        for(i in 0 until numberOfBeams) {
            val beamPath = Path()
            beamPath.addRect(beamRect, Path.Direction.CW)
            rotationMatrix.reset()
            rotationMatrix.setRotate(i * beamAngle, cx, cy)
            beamPath.transform(rotationMatrix)
            path.addPath(beamPath)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        Timber.d("onMeasure: %s %s", MeasureSpec.toString(widthMeasureSpec), MeasureSpec.toString(heightMeasureSpec))

        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthSpecMode) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> widthSpecSize
            else -> max(DEFAULT_WIDTH, suggestedMinimumWidth)
        }

        val height = when (heightSpecMode) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> heightSpecSize
            else -> max(DEFAULT_HEIGHT, suggestedMinimumHeight)
        }
        setMeasuredDimension(width, height)
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {

            val cx = width/2f
            val cy = height/2f

            drawCircle(cx, cy, circleRadius, paint)

            rotationMatrix.reset()
            rotationMatrix.setRotate(phase, cx, cy)
            path.transform(rotationMatrix)

            drawPath(path, paintBeam)

//            val beam = RectF(
//                cx - beamWidth / 2,
//                cy - circleRadius - beamGap - beamLength,
//                cx + beamWidth / 2,
//                cy - circleRadius - beamGap)
//
//            drawRect(beam, paintBeam)
//
//            val beamAngle: Float = 360f/numberOfBeams
//
//            for(i in 1 until numberOfBeams) {
//                path.reset()
//                path.addRect(beam, Path.Direction.CW)
//                rotationMatrix.reset()
//                rotationMatrix.setRotate(beamAngle * i, cx, cy)
//                path.transform(rotationMatrix)
//                //beams.add(beam)
//                drawPath(path, paintBeam)
//            }
        }
    }
}