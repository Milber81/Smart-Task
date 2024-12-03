package com.smart.task.ui.dailyDetails

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.task.R
import com.smart.task.databinding.FragmentDailyDetailsBinding
import com.smart.task.ui.UiModule
import com.smart.task.ui.helpers.loadIcon
import kotlinx.coroutines.launch

class DailyDetailsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDailyDetailsBinding

    companion object {
        const val TAG = "dailyDetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm = UiModule.provideMainViewModel

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val styleResId: Int = R.style.ModalBottomSheetDialog

        setStyle(DialogFragment.STYLE_NORMAL, styleResId)

        return BottomSheetDialog(requireContext(), styleResId)
    }

    override fun onDestroyView() {
        binding.dayImage.setImageBitmap(null)
        binding.cityName.text = null
        binding.averageTemperature.text = null
        binding.lowestTemp.text = null
        binding.highestTemp.text = null

        super.onDestroyView()
    }
}
