package com.mobilancer.timelapsecalculator.ui.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.addTextChangedListener
import com.mobilancer.timelapsecalculator.R
import com.mobilancer.timelapsecalculator.databinding.ViewRulerInputBinding
import kotlin.math.floor

class RulerInputViewComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

) : LinearLayoutCompat(context,attrs,defStyleAttr) {
//    init {
//        // get the inflater service from the android system
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        // inflate the layout into "this" component
//        inflater.inflate(R.layout.view_ruler_input, this)
//    }

    private val binding : ViewRulerInputBinding by lazy{
        ViewRulerInputBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private var shouldChangeRulerView = true
    private var value: Int = 0

    fun render(inputProperties: RulerInputProperties) = with(binding){

        val ctx = binding.root.context
        tiValue.hint = ctx.getString(inputProperties.hint)
        tiValue.setText(inputProperties.initialValue.toString())
        textViewSelectionLabel.text = ctx.getString(inputProperties.title)
        rulerViewSelection.setValue(inputProperties.initialValue.toFloat())
        value = inputProperties.initialValue

        if(inputProperties.isMinuteHintVisible){
            textViewMinuteRepresentation.visibility = View.VISIBLE
            val minute = calculateMinuteRepresentation(value)
            textViewMinuteRepresentation.text = ctx.getString(R.string.min_format, String.format("%.1f", minute))
        }else{
            textViewMinuteRepresentation.visibility = View.GONE
        }

        rulerViewSelection.setValueListener {
            shouldChangeRulerView = false
            value = it.toInt()
            tiValue.setText(value.toString())

            if(inputProperties.isMinuteHintVisible) {
                val minute = calculateMinuteRepresentation(value)
                textViewMinuteRepresentation.text = ctx.getString(R.string.min_format, String.format("%.1f", minute))
            }

        }

        tiValue.addTextChangedListener {
            if(shouldChangeRulerView){
                val currentValue = it.toString().trim().toIntOrNull()
                currentValue?.run {
                    rulerViewSelection.setValue(this.toFloat())
                    value = this
                    if(inputProperties.isMinuteHintVisible) {
                        val minute = calculateMinuteRepresentation(value)
                        textViewMinuteRepresentation.text = ctx.getString(R.string.min_format, String.format("%.1f", minute))
                    }
                }
            }
            shouldChangeRulerView = true
        }

    }

    private fun calculateMinuteRepresentation(value: Int): Float {
       return (value.toDouble() / 60).toFloat()
    }

}

data class RulerInputProperties(
    @StringRes var hint : Int,
    @StringRes var title : Int,
    var initialValue : Int = 0,
    var min : Float = 0.0F,
    var max : Float = 1000.0F,
    var isMinuteHintVisible : Boolean = false
    )