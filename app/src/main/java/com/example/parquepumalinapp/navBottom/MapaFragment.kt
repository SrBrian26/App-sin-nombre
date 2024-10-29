package com.example.parquepumalinapp.navBottom

import android.graphics.Matrix
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.parquepumalinapp.R
import com.example.parquepumalinapp.databinding.FragmentMapaBinding

class MapaFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private val matrix = Matrix()
    private var scaleFactor = 1.0f
    private var lastPoint = PointF()
    private var rotationAngle = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicializa el binding
        val binding = FragmentMapaBinding.inflate(inflater, container, false)

        // Inicializa imageView a través del binding
        imageView = binding.transformableImageView

        // Inicializa el detector de escala para gestionar el zoom
        scaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())

        // Configura el evento táctil para la imagen
        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            handleTouch(event)
            true
        }

        // Devuelve la vista raíz del binding
        return binding.root
    }

    // Clase para manejar el zoom con gestos multitouch
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.5f, 5.0f) // Límite de zoom (0.5x a 5x)
            matrix.setScale(scaleFactor, scaleFactor)
            imageView.imageMatrix = matrix
            return true
        }
    }

    // Maneja el desplazamiento y la rotación
    private fun handleTouch(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastPoint.set(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastPoint.x
                val dy = event.y - lastPoint.y
                matrix.postTranslate(dx, dy)
                imageView.imageMatrix = matrix
                lastPoint.set(event.x, event.y)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                rotationAngle = calculateRotationAngle(event)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                matrix.postRotate(rotationAngle)
                imageView.imageMatrix = matrix
            }
        }
    }

    // Calcula el ángulo de rotación en multitouch
    private fun calculateRotationAngle(event: MotionEvent): Float {
        if (event.pointerCount >= 2) {
            val dx = event.getX(1) - event.getX(0)
            val dy = event.getY(1) - event.getY(0)
            return Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble())).toFloat()
        }
        return 0f
    }
}
