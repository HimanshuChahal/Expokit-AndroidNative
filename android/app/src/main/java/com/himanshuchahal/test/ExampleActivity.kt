package com.himanshuchahal.test

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import com.himanshuchahal.test.draw.Draw

class ExampleActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var poseDetector: PoseDetector
    private lateinit var videoCapture: VideoCapture
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

    lateinit var previewView: PreviewView
    lateinit var textView: TextView
    lateinit var detailsTextView: TextView
    lateinit var parentLayout: RelativeLayout

    companion object {
        var arr: Array<Int> = arrayOf(10, 0, 0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        checkPermissions()

        cameraProviderFuture = ProcessCameraProvider.getInstance(applicationContext)

        val preview = Preview.Builder().build()

        previewView = findViewById(R.id.previewView)
        textView = findViewById(R.id.textView)
        detailsTextView = findViewById(R.id.detailsTextView)
        parentLayout = findViewById(R.id.parentLayout)

        preview.setSurfaceProvider(previewView.surfaceProvider)

        val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()

        cameraProviderFuture.get().bindToLifecycle(this, cameraSelector, preview)

        var timer = object: CountDownTimer(15000, 1000){
            override fun onTick(p0: Long) {
                textView.text = (p0/1000).toInt().toString()

                if(p0 >= 5000)
                {
                    detailsTextView.text = "Keep a distance of 5 feet from the camera"
                } else
                {
                    detailsTextView.text = "Detection starting in "
//                    tts.speak("Hello How are you", TextToSpeech.QUEUE_FLUSH, null, "")
                }

            }
            override fun onFinish() {
                arr[2] = 1

                textView.visibility = View.GONE
                detailsTextView.visibility = View.GONE
                cameraProviderFuture.addListener(Runnable {
                    val cameraProvider = cameraProviderFuture.get()
                    bindPreview(cameraProvider)

                }, ContextCompat.getMainExecutor(applicationContext))

                val options = AccuratePoseDetectorOptions.Builder()
                        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
                        .build()

                poseDetector = PoseDetection.getClient(options)
            }
        }

        timer.start()

    }

    fun checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    @SuppressLint("RestrictedApi", "UnsafeExperimentalUsageError", "NewApi")
    private fun bindPreview(cameraProvider: ProcessCameraProvider){

        Log.d("Check:","inside bind preview")

        val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()
        val point = Point()
        val size = display?.getRealSize(point)

        videoCapture = VideoCapture.Builder()
                .setTargetResolution(Size(previewView.width,previewView.height))
                .build()


        val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetResolution(Size(point.x,point.y))
                .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), ImageAnalysis.Analyzer { imageProxy ->

            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val image = imageProxy.image

            if(image!=null){

                val processImage = InputImage.fromMediaImage(image,rotationDegrees)

                poseDetector.process(processImage)
                        .addOnSuccessListener {

                            if(parentLayout.childCount>1){
                                parentLayout.removeViewAt(1)
                            }
                            if(it.allPoseLandmarks.isNotEmpty()){

                                if(parentLayout.childCount>1){
                                    parentLayout.removeViewAt(1)
                                }

                                val element = Draw(applicationContext,it)
                                parentLayout.addView(element)
                            }
                            imageProxy.close()
                        }
                        .addOnFailureListener{


                            imageProxy.close()
                        }
            }


        })

        cameraProvider.bindToLifecycle(this,cameraSelector,imageAnalysis)

    }
}