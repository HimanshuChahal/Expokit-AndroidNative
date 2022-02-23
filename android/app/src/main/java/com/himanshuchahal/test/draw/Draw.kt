package com.himanshuchahal.test.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import com.himanshuchahal.test.ExampleActivity
import kotlin.math.atan2

class Draw(context: Context?, var pose: Pose) : View(context) {
    lateinit var boundaryPaint: Paint
    lateinit var leftPaint: Paint
    lateinit var rightPaint: Paint
    lateinit var textPaint: Paint
    lateinit var textPaint_green: Paint

    init{
        init()
    }

    private fun init(){
        boundaryPaint = Paint()
        boundaryPaint.color = Color.GREEN
        boundaryPaint.strokeWidth = 6f
        boundaryPaint.style = Paint.Style.STROKE

        leftPaint = Paint()
        leftPaint.strokeWidth = 4f
        leftPaint.color = Color.WHITE

        rightPaint = Paint()
        rightPaint.strokeWidth = 4f
        rightPaint.color = Color.WHITE

        textPaint = Paint()
        textPaint.color = Color.RED
        textPaint.textSize = 50f
        textPaint.typeface = Typeface.MONOSPACE

        textPaint_green = Paint()
        textPaint_green.color = Color.DKGRAY
        textPaint_green.textSize = 50f
        textPaint_green.typeface = Typeface.MONOSPACE

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val landmarks = pose.allPoseLandmarks

        for (landmark in landmarks) {

            canvas?.drawCircle(translateX(landmark.position.x),translateY(landmark.position.y),6.0f,boundaryPaint)

        }

        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

        val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
        val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
        val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
        val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

//        val k_angle = getAngle(leftHip, leftKnee, leftAnkle)
//        val b_angle = getAngle(leftShoulder, leftHip, leftKnee)
//
////        if(b_angle < 45)
////        {
////            canvas?.drawText("Try to keep your back straight", 100f, 80f, textPaint)
////            if(!MainActivity.tts.isSpeaking) {
////                MainActivity.tts.speak("Back straight", TextToSpeech.QUEUE_FLUSH, null, "")
////            }
////        }
//
//        if(leftAnkle.inFrameLikelihood < 0.25 && leftFootIndex.inFrameLikelihood < 0.25)
//        {
//            canvas?.drawText("Legs not visible", 100f, 160f, textPaint)
//        }
//
//        if(Math.abs(leftHip.position.x - rightHip.position.x) >= 70)
//        {
//            canvas?.drawText("Face the right side", 100f, 240f, textPaint)
//        }
//
//        if(k_angle in 60.0..120.0 && ExampleActivity.arr[1] == 1)
//        {
//            ExampleActivity.arr[3] = 1
//        }
//
//        if(/*(leftHip.position.y <= leftKnee.position.y - 75)*/k_angle > 150 && b_angle > 150 && ExampleActivity.arr[2] == 1)
//        {
//            ExampleActivity.arr[1] = 1
//        }
//
//        if(ExampleActivity.arr[1] == 1)
//        {
//            if((k_angle < 60) && (Math.abs(leftHip.position.x - rightHip.position.x) < 50) && (leftAnkle.inFrameLikelihood > 0.7 && leftFootIndex.inFrameLikelihood > 0.7)) {
//                ExampleActivity.arr[1] = 0
//                ExampleActivity.arr[0]--
//                ExampleActivity.arr[3] = 0
//
//                if(ExampleActivity.arr[0] == 0)
//                {
//                    ExampleActivity.arr[2] = 0
//                }
//            }
//        }
//
//        if(ExampleActivity.arr[3] == 1 && (k_angle > 150 && b_angle > 150))
//        {
//            canvas?.drawText("Partial squat, go down further", 100f, 320f, textPaint)
//
//            ExampleActivity.arr[3] = 0
//        }
//
//        canvas?.drawText("Count " + ExampleActivity.arr[0], 100f, 400f, textPaint_green)

        canvas?.drawLine(translateX(leftShoulder.position.x), translateY(leftShoulder.position.y), translateX(rightShoulder.position.x), translateY(rightShoulder.position.y), boundaryPaint)
        canvas?.drawLine(translateX(leftHip.position.x), translateY(leftHip.position.y), translateX(rightHip.position.x), translateY(rightHip.position.y), boundaryPaint)

        //Left body

        canvas?.drawLine(translateX(leftShoulder.position.x),translateY(leftShoulder.position.y),translateX(leftElbow.position.x),translateY(leftElbow.position.y),leftPaint)
        canvas?.drawLine(translateX(leftElbow.position.x),translateY(leftElbow.position.y),translateX(leftWrist.position.x),translateY(leftWrist.position.y),leftPaint)
        canvas?.drawLine(translateX(leftShoulder.position.x),translateY(leftShoulder.position.y),translateX(leftHip.position.x),translateY(leftHip.position.y),leftPaint)
        canvas?.drawLine(translateX(leftHip.position.x),translateY(leftHip.position.y),translateX(leftKnee.position.x),translateY(leftKnee.position.y),leftPaint)
        canvas?.drawLine(translateX(leftKnee.position.x),translateY(leftKnee.position.y),translateX(leftAnkle.position.x),translateY(leftAnkle.position.y),leftPaint)
        canvas?.drawLine(translateX(leftWrist.position.x),translateY(leftWrist.position.y),translateX(leftThumb.position.x),translateY(leftThumb.position.y),leftPaint)
        canvas?.drawLine(translateX(leftWrist.position.x),translateY(leftWrist.position.y),translateX(leftPinky.position.x),translateY(leftPinky.position.y),leftPaint)
        canvas?.drawLine(translateX(leftWrist.position.x),translateY(leftWrist.position.y),translateX(leftIndex.position.x),translateY(leftIndex.position.y),leftPaint)
        canvas?.drawLine(translateX(leftIndex.position.x),translateY(leftIndex.position.y),translateX(leftPinky.position.x),translateY(leftPinky.position.y),leftPaint)
        canvas?.drawLine(translateX(leftAnkle.position.x),translateY(leftAnkle.position.y),translateX(leftHeel.position.x),translateY(leftHeel.position.y),leftPaint)
        canvas?.drawLine(translateX(leftHeel.position.x),translateY(leftHeel.position.y),translateX(leftFootIndex.position.x),translateY(leftFootIndex.position.y),leftPaint)

        //Right body
        canvas?.drawLine(translateX(rightShoulder.position.x),translateY(rightShoulder.position.y),translateX(rightElbow.position.x),translateY(rightElbow.position.y),rightPaint)
        canvas?.drawLine(translateX(rightElbow.position.x),translateY(rightElbow.position.y),translateX(rightWrist.position.x),translateY(rightWrist.position.y),rightPaint)
        canvas?.drawLine(translateX(rightShoulder.position.x),translateY(rightShoulder.position.y),translateX(rightHip.position.x),translateY(rightHip.position.y),rightPaint)
        canvas?.drawLine(translateX(rightHip.position.x),translateY(rightHip.position.y),translateX(rightKnee.position.x),translateY(rightKnee.position.y),rightPaint)
        canvas?.drawLine(translateX(rightKnee.position.x),translateY(rightKnee.position.y),translateX(rightAnkle.position.x),translateY(rightAnkle.position.y),rightPaint)
        canvas?.drawLine(translateX(rightWrist.position.x),translateY(rightWrist.position.y),translateX(rightThumb.position.x),translateY(rightThumb.position.y),rightPaint)
        canvas?.drawLine(translateX(rightWrist.position.x),translateY(rightWrist.position.y),translateX(rightPinky.position.x),translateY(rightPinky.position.y),rightPaint)
        canvas?.drawLine(translateX(rightWrist.position.x),translateY(rightWrist.position.y),translateX(rightIndex.position.x),translateY(rightIndex.position.y),rightPaint)
        canvas?.drawLine(translateX(rightIndex.position.x),translateY(rightIndex.position.y),translateX(rightPinky.position.x),translateY(rightPinky.position.y),rightPaint)
        canvas?.drawLine(translateX(rightAnkle.position.x),translateY(rightAnkle.position.y),translateX(rightHeel.position.x),translateY(rightHeel.position.y),rightPaint)
        canvas?.drawLine(translateX(rightHeel.position.x),translateY(rightHeel.position.y),translateX(rightFootIndex.position.x),translateY(rightFootIndex.position.y),rightPaint)

    }

    // 1  2  3  4
    // x1 y1 x2 y2

    @RequiresApi(Build.VERSION_CODES.R)
    fun translateX(x: Float): Float {

        // you will need this for the inverted image in case of using front camera
        // return context.display?.width?.minus(x)!!

        return width - (x/2)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun translateY(y: Float): Float {

        return y/2
    }

    fun getAngle(firstPoint: PoseLandmark, midPoint: PoseLandmark, lastPoint: PoseLandmark): Double {
        var result = Math.toDegrees(atan2(lastPoint.getPosition().y - midPoint.getPosition().y,
            lastPoint.getPosition().x - midPoint.getPosition().x).toDouble()
                - atan2(firstPoint.getPosition().y - midPoint.getPosition().y,
            firstPoint.getPosition().x - midPoint.getPosition().x).toDouble())
        result = Math.abs(result) // Angle should never be negative
        if (result > 180) {
            result = 360.0 - result // Always get the acute representation of the angle
        }
        return result
    }

}