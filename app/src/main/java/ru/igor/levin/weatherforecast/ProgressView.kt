package ru.igor.levin.weatherforecast

import android.animation.*
import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min

const val DEFAULT_WIDTH: Int = 300
const val DEFAULT_HEIGHT: Int = 300

const val MAX_CIRCLE_RADIUS: Float = 200f

const val MAX_BEAM_GAP: Float = 30f
const val MIN_BEAM_GAP: Float = 10f

class ProgressView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var viewType: Int = 0
    private var viewColor: Int = Color.RED

    private var circleRadius = 100f
    private var beamWidth = 15f
    private var beamLength = 50f
    private var beamGap = MIN_BEAM_GAP
    private var phase = 0f

    private val rotationMatrix: Matrix = Matrix()
    private val mainBeams: Array<RectF> = Array(4) {RectF()}
    private val mainBeamsPath: Path = Path()

    private var circlePaint: Paint
    private val beamPaint: Paint

    private val onDownListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }

    private val beamPhaseAnimator = ValueAnimator.ofFloat(0f, 180f).apply {
        interpolator = CustomInterpolator()
        duration = 5000
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener {
            phase = it.animatedValue as Float
            Timber.d("Phase: %f", phase)
            //invalidate() // because of using AnimatorSet
        }
    }

    private val beamGapAnimator = ValueAnimator.ofFloat(MIN_BEAM_GAP, MAX_BEAM_GAP).apply {
        interpolator = CustomInterpolator()

        duration = 5000
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener {
            beamGap = it.animatedValue as Float
            Timber.d("Gap: %f", phase)
            invalidate()
        }
    }

    private val progressAnimator = AnimatorSet().apply {
        play(beamPhaseAnimator).with(beamGapAnimator)
    }

    private val detector: GestureDetector = GestureDetector(context, onDownListener)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0).apply {
            try {
                viewType = getInteger(R.styleable.ProgressView_type, 0)
                viewColor = getColor(R.styleable.ProgressView_color, Color.RED)
            } finally {
                recycle()
            }
        }

        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = viewColor
            style = if (viewType == 1) Paint.Style.FILL else Paint.Style.STROKE
            strokeWidth = 12f
        }

        beamPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = viewColor
            style = Paint.Style.FILL
        }
    }

    fun startProgress() {
        progressAnimator.start()
    }

    fun stopProgress() {
        progressAnimator.cancel()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Timber.d("onTouchEvent: %s", event.toString())

        return detector.onTouchEvent(event).let {result ->
            if (result) {
                if (progressAnimator.isStarted) {
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

        circleRadius = (min(w,h) - 2f* beamLength - 2f* MAX_BEAM_GAP)/2f
        circleRadius = min(circleRadius, MAX_CIRCLE_RADIUS)
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

            drawCircle(cx, cy, circleRadius, circlePaint)

            mainBeams[0].set(cx - beamWidth / 2,
                cy - circleRadius - beamGap - beamLength,
                cx + beamWidth / 2,
                cy - circleRadius - beamGap)

            mainBeams[1].set(cx + circleRadius + beamGap,
                cy - beamWidth/2,
                cx + circleRadius + beamGap + beamLength,
                cy + beamWidth/2)

            mainBeams[2].set(cx - beamWidth / 2,
                cy + circleRadius + beamGap,
                cx + beamWidth / 2,
                cy + circleRadius + beamGap + beamLength)

            mainBeams[3].set(cx - circleRadius - beamGap - beamLength,
                cy - beamWidth/2,
                cx - circleRadius - beamGap,
                cy + beamWidth/2)

            mainBeamsPath.reset()
            for (beam in mainBeams) {
                mainBeamsPath.addRect(beam, Path.Direction.CW)
            }

            rotationMatrix.reset()
            rotationMatrix.setRotate(phase, cx, cy)
            mainBeamsPath.transform(rotationMatrix)
            drawPath(mainBeamsPath, beamPaint)

            rotationMatrix.reset()
            rotationMatrix.setRotate(45f, cx, cy)
            mainBeamsPath.transform(rotationMatrix)
            drawPath(mainBeamsPath, beamPaint)
        }
    }

    class CustomInterpolator: TimeInterpolator {

        override fun getInterpolation(input: Float): Float {
            val res: Float = (cos((input * 2f + 1) * Math.PI) / 2.0f).toFloat() + 0.5f
            Timber.d("Intp: %f %f", input, res)
            return res
        }
    }
}