package com.smart.task.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.task.R
import com.smart.task.databinding.AddCityBinding

class AddCityFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddCityFragment"
    }

    private lateinit var binding: AddCityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = AddCityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val styleResId = R.style.ModalBottomSheetDialog
        setStyle(DialogFragment.STYLE_NORMAL, styleResId)

        return BottomSheetDialog(requireContext(), styleResId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtCityName.requestFocus()

        fillCityViews()

        binding.bntApply.setOnClickListener {

            val cityName = binding.edtCityName.text
            val cityLat = binding.edtLatitude.text.toString()
            val cityLng = binding.edtLongitude.text.toString()
            val cityState = binding.edtState.text
            val cityAbbr = binding.edtStateAbbr.text

            if (cityName.isEmpty()
                || cityLat.isEmpty()
                || cityLng.isEmpty()
                || cityState.isEmpty()
                || cityAbbr.isEmpty()
            )
                return@setOnClickListener

        }
    }

    private fun fillCityViews(){
        val linearLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        binding.cities.addView(linearLayout)
    }

}
