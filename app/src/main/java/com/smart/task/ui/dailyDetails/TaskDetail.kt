package com.smart.task.ui.dailyDetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.smart.task.R
import com.smart.task.databinding.FragmentDailyDetailsBinding
import com.smart.task.ui.UiModule
import kotlinx.coroutines.launch

class TaskDetail : Fragment() {

    private lateinit var binding: FragmentDailyDetailsBinding
    private var taskId = ""

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

        binding.navigateBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        lifecycleScope.launch {
            vm.data.collect {
                it?.let { task ->
                    taskId = task.id
                    binding.txtTitle.text = task.title
                    val dueDate = binding.root.findViewById<TextView>(R.id.dueDate)
                    val daysLeft = binding.root.findViewById<TextView>(R.id.daysLeft)
                    val status = binding.root.findViewById<TextView>(R.id.txtStatus)
                    val statusImage = binding.root.findViewById<ImageView>(R.id.imgStatus)
                    dueDate.text = it.date
                    daysLeft.text = it.daysOffset
                    status.text = it.status

                    if(it.status == "Resolved"){
                        statusImage.setImageResource(R.drawable.sign_resolved)
                    }else{
                        statusImage.setImageResource(R.drawable.unresolved_sign)
                    }
                }
            }
        }

        setBg("#15897b", binding.btnResolve)
        setBg("#EF4B5E", binding.btnCantResolve)

        binding.btnResolve.setOnClickListener {
            vm.resolveTask(taskId)
        }

        binding.btnCantResolve.setOnClickListener {

        }
    }

    private fun setBg(color: String, view: View){
        val backgroundDrawable = MaterialShapeDrawable().apply {
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(12f)
                .build()
            setTint(Color.parseColor(color))
        }

        view.background = backgroundDrawable
    }
}