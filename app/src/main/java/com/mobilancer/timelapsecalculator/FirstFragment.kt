package com.mobilancer.timelapsecalculator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.mobilancer.timelapsecalculator.databinding.FragmentFirstBinding
import com.mobilancer.timelapsecalculator.domain.CalculationResultMode
import com.mobilancer.timelapsecalculator.domain.CalculationType
import com.mobilancer.timelapsecalculator.domain.TimelapseCalculation
import com.mobilancer.timelapsecalculator.ui.uicomponents.CalculationResultInputProperties
import com.mobilancer.timelapsecalculator.ui.uicomponents.RulerInputChangeListener
import com.mobilancer.timelapsecalculator.ui.uicomponents.RulerInputProperties
import com.mobilancer.timelapsecalculator.util.fromHtml

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), RulerInputChangeListener {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderViews()

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
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
            setOnValueChangeListener(this@FirstFragment)
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
            setOnValueChangeListener(this@FirstFragment)
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
    }

}