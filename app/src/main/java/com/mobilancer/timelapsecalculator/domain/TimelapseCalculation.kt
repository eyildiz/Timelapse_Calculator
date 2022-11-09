package com.mobilancer.timelapsecalculator.domain

import kotlin.math.roundToInt

class TimelapseCalculation {
    companion object {
        fun calculateShutterInterval(eventDuration: Int, clipDuration: Int, fps: Int): Int {
            return (eventDuration.toFloat() / (clipDuration * fps)).roundToInt()
        }

        fun calculateClipLength(eventDuration: Int, shutterInterval: Int, fps: Int): Int {
            return ((eventDuration.toFloat() / shutterInterval) / fps).roundToInt()
        }

        fun calculateEventDuration(clipDuration: Int, shutterInterval: Int, fps: Int): Int {
            return clipDuration * shutterInterval * fps
        }

        fun calculateTotalImages(clipLength: Int, fps: Int): Int {
            return clipLength * fps
        }

        fun calculateTotalMemory(totalImageCount: Int, averageSizeInMb: Int): Float {
            return ((totalImageCount * averageSizeInMb).toFloat() * 0.0009765625F)
        }
    }
}