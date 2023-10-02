package com.mobilancer.timelapsecalculator.ui.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.mobilancer.timelapsecalculator.R
import com.mobilancer.timelapsecalculator.databinding.FragmentFirstBinding
import com.mobilancer.timelapsecalculator.databinding.FragmentShutterIntervalBinding
import com.mobilancer.timelapsecalculator.domain.CalculationResultMode
import com.mobilancer.timelapsecalculator.domain.CalculationType
import com.mobilancer.timelapsecalculator.ui.uicomponents.CalculationResultInputProperties
import com.mobilancer.timelapsecalculator.ui.uicomponents.RulerInputChangeListener
import com.mobilancer.timelapsecalculator.ui.uicomponents.RulerInputProperties
import kotlinx.coroutines.launch

class ShutterIntervalFragment : Fragment(), RulerInputChangeListener {
    private var _binding: FragmentShutterIntervalBinding? = null
    private val viewModel: ShutterIntervalViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShutterIntervalBinding.inflate(inflater, container, false)

        setObservers()
        return binding.root

    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->

                uiState.calculationData?.let {
                    binding.calculationResult.setData(uiState.calculationData)
                    viewModel.calculationResultRendered()
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderViews()
    }

    private fun renderViews() = with(binding) {

        with(rulerInputClipLength) {
            render(
                RulerInputProperties(
                    hint = R.string.label_second_shortened,
                    title = R.string.label_please_input_clip_length,
                    initialValue = 10,
                    min = 1F,
                    max = 1000F
                )
            )
            setOnValueChangeListener(this@ShutterIntervalFragment)
        }

        with(rulerInputEventDuration) {
            render(
                RulerInputProperties(
                    hint = R.string.label_second_shortened,
                    title = R.string.label_please_input_clip_length,
                    initialValue = 10,
                    min = 1F,
                    max = 1000F
                )
            )
            setOnValueChangeListener(this@ShutterIntervalFragment)
        }

        chipGroupFpsChoice.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let { chipView ->
                Toast.makeText(context, chip.text, Toast.LENGTH_SHORT).show()
            } ?: kotlin.run {
            }
        }

        calculationResult.render(
            CalculationResultInputProperties(
                initialMode = CalculationResultMode.NO_RESULT,
                initalCalculationType = CalculationType.SHUTTER_INTERVAL
            )
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onValueChanged(value: String) {
        Log.d("TIMELAPSE", "Value Changed $value")
        //viewModel.calculateResult(binding.rulerInputEventDuration.value)
    }
}