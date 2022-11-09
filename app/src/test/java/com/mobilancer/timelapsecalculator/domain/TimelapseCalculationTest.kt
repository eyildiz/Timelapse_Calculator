package com.mobilancer.timelapsecalculator.domain

import org.junit.Assert.*
import org.junit.Test

class TimelapseCalculationTest {
    @Test
    fun `calculate and round shutter interval correct`() {
        val mustBeRounded = TimelapseCalculation.calculateShutterInterval(1200, 13, 24)
        assertEquals(4, mustBeRounded)

        val noNeedToBeRounded = TimelapseCalculation.calculateShutterInterval(1200, 10, 24)
        assertEquals(5, noNeedToBeRounded)
    }

    @Test
    fun `calculate and round clip length correct`() {
        val mustBeRounded = TimelapseCalculation.calculateClipLength(1800, 7, 24)
        assertEquals(11, mustBeRounded)
    }

    @Test
    fun `calculate event duration correct`() {
        val duration = TimelapseCalculation.calculateEventDuration(10, 5, 24)
        assertEquals(1200, duration)
    }

    @Test
    fun `calculate total image count correct`() {
        val totalImages = TimelapseCalculation.calculateTotalImages(10, 24)
        assertEquals(240, totalImages)
    }

    @Test
    fun `calculate total memory need correct`() {
        val totalImages = TimelapseCalculation.calculateTotalImages(10, 24)
        val totalMemory = TimelapseCalculation.calculateTotalMemory(totalImages, 20)
        assertEquals(4.6875F, totalMemory)
    }
}