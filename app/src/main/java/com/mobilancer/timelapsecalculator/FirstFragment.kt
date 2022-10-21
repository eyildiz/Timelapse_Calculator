package com.mobilancer.timelapsecalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.mobilancer.timelapsecalculator.databinding.FragmentFirstBinding
import com.mobilancer.timelapsecalculator.ui.uicomponents.RulerInputProperties

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

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

    private fun renderViews()  = with(binding){
       rulerInputClipLength.render(
           RulerInputProperties(hint = R.string.label_second_shortened, title = R.string.label_please_input_clip_length, initialValue = 10,min=1F,max=1000F))
       rulerInputEventDuration.render(
           RulerInputProperties(hint = R.string.label_second_shortened, title = R.string.label_please_input_even_duration, isMinuteHintVisible = true, initialValue = 60,min=1F,max=1000F))

        chipGroupFpsChoice.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {chipView ->
                Toast.makeText(context, chip.text, Toast.LENGTH_SHORT).show()
            } ?: kotlin.run {
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}