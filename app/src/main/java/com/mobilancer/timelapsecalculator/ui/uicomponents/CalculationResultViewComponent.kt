package com.mobilancer.timelapsecalculator.ui.uicomponents

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.card.MaterialCardView
import com.mobilancer.timelapsecalculator.R
import com.mobilancer.timelapsecalculator.databinding.ViewCalculationResultBinding
import com.mobilancer.timelapsecalculator.domain.CalculationResultMode
import com.mobilancer.timelapsecalculator.domain.CalculationType

class CalculationResultViewComponent  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : MaterialCardView(context,attrs,defStyleAttr) {

    private val binding : ViewCalculationResultBinding by lazy{
        ViewCalculationResultBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private lateinit var ctx: Context
    private lateinit var currentMode : CalculationResultMode

    fun render(inputProperties: CalculationResultInputProperties) = with(binding){
        ctx = binding.root.context
        setMode(inputProperties.initialMode)
        setCalculationType(inputProperties.initalCalculationType)
    }

    fun setCalculationType(type : CalculationType){
        when(type){
            CalculationType.CLIP_DURATION -> run {
                with(binding.txtResultLabel){
                    text = ctx.getString(R.string.label_clip_duration)
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie,0,0,0)
                }
            }

            CalculationType.EVENT_DURATION -> run {
                with(binding.txtResultLabel){
                    text = ctx.getString(R.string.label_event_duration)
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_timer,0,0,0)
                }
            }

            CalculationType.SHUTTER_INTERVAL -> run {
                with(binding.txtResultLabel){
                    text = ctx.getString(R.string.label_shutter_interval)
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_shutter,0,0,0)
                }
            }
        }
    }
    fun setMode(mode : CalculationResultMode){
        when(mode){
            CalculationResultMode.NO_RESULT -> {
                binding.layoutContainer.visibility = View.GONE
            }
            CalculationResultMode.RESULT_CHANGED -> {
                binding.layoutContainer.visibility = View.VISIBLE
                binding.txtConfigChangedState.visibility = View.INVISIBLE
            }
            CalculationResultMode.RESULT_VISIBLE -> {
                binding.layoutContainer.visibility = View.VISIBLE
                binding.txtConfigChangedState.visibility = View.INVISIBLE
            }
        }

    }
    fun setData(data : CalculationResultData){
        with(binding){
            txtResultLabel.text = data.changeableResultValue
            txtResultStorage.text = data.totalStorageNeed
            txtResultTotalPhotos.text = data.totalPhotoCount
        }
    }
}

data class CalculationResultInputProperties(
    val initalCalculationType : CalculationType = CalculationType.SHUTTER_INTERVAL,
    val initialMode : CalculationResultMode = CalculationResultMode.NO_RESULT
)

data class CalculationResultData(
    val changeableResultValue : String,
    val totalPhotoCount : String,
    val totalStorageNeed : String
)