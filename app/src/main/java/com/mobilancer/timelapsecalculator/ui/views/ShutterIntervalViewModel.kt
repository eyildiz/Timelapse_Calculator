package com.mobilancer.timelapsecalculator.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilancer.timelapsecalculator.domain.TimelapseCalculation
import com.mobilancer.timelapsecalculator.ui.uicomponents.CalculationResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShutterIntervalViewModel : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(
        UiState(
            userMessage = "",
            calculationData = null
        )
    )
    val uiState: StateFlow<UiState> = _uiState

    fun calculateResult(eventDuration: Int, shutterInterval: Int, fps: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val shutterInterval = TimelapseCalculation.calculateShutterInterval(eventDuration,shutterInterval,fps)
            val clipLength = TimelapseCalculation.calculateClipLength(eventDuration,shutterInterval,fps)
            val totalImageCount = TimelapseCalculation.calculateTotalImages(clipLength,fps)
            val totalStorageNeed = TimelapseCalculation.calculateTotalMemory(totalImageCount,15)
            _uiState.update {
                it.copy(
                    calculationData = CalculationResultData(
                        changeableResultValue = shutterInterval.toString(),
                        totalPhotoCount = totalImageCount.toString(),
                        totalStorageNeed = "$totalStorageNeed GB"
                    )
                )
            }
        }
    }

    fun calculationResultRendered(){
        _uiState.update {
            it.copy(
                calculationData = null
            )
        }
    }


    data class UiState(
        val userMessage: String,
        val calculationData: CalculationResultData?
    )

}