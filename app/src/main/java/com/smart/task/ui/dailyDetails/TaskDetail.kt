package com.smart.task.ui.dailyDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smart.task.databinding.FragmentDailyDetailsBinding
import com.smart.task.ui.UiModule

class TaskDetail: Fragment() {

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

        binding.navigateBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}