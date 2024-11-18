import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView

class ZoomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
    ) : AppCompatImageView(context, attrs, defStyleAttr) {

        private var scaleFactor = 1.0f
        private val matrix = Matrix()
        private val scaleDetector: ScaleGestureDetector

        private var lastX = 0f
        private var lastY = 0f
        private var startX = 200f
        private var startY = 300f
        private val initialScale = 2.0f

        init {
            // Establecer escala inicial y posicionar la vista en la sección deseada
            matrix.postScale(initialScale, initialScale)
            matrix.postTranslate(-startX * initialScale, -startY * initialScale)
            imageMatrix = matrix

            scaleDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    scaleFactor *= detector.scaleFactor
                    scaleFactor = scaleFactor.coerceIn(1.0f, 5.0f) // Limita el zoom entre 1x y 5x
                    matrix.setScale(scaleFactor, scaleFactor)
                    imageMatrix = matrix
                    return true
                }
            })
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            scaleDetector.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.x - lastX
                    val dy = event.y - lastY
                    matrix.postTranslate(dx, dy)
                    imageMatrix = matrix
                    lastX = event.x
                    lastY = event.y
                }
            }
            return true
        }
    }
