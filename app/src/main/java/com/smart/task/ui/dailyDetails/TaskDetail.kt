package com.smart.task.ui.dailyDetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.smart.task.R
import com.smart.task.databinding.FragmentDailyDetailsBinding
import com.smart.task.domain.Task
import com.smart.task.ui.UiModule
import kotlinx.coroutines.launch

class TaskDetail : Fragment() {

    private lateinit var binding: FragmentDailyDetailsBinding
    private var taskId = ""
    val vm = UiModule.provideMainViewModel

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


        binding.navigateBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        lifecycleScope.launch {
            vm.data.collect {
                it?.let { task ->
                    taskId = task.id
                    binding.txtTitle.text = task.title
                    binding.txtDescription.text = task.description
                    val dueDate = binding.root.findViewById<TextView>(R.id.dueDate)
                    val daysLeft = binding.root.findViewById<TextView>(R.id.daysLeft)
                    val status = binding.root.findViewById<TextView>(R.id.txtStatus)
                    val statusImage = binding.root.findViewById<ImageView>(R.id.imgStatus)
                    dueDate.text = it.date
                    daysLeft.text = it.daysOffset
                    status.text = it.statusText

                    when(it.status){
                        Task.UNRESOLVED -> {
                            binding.btnResolve.visibility = View.VISIBLE
                            binding.btnCantResolve.visibility = View.VISIBLE
                            statusImage.visibility = View.GONE
                        }
                        Task.RESOLVED -> {
                            binding.btnResolve.visibility = View.GONE
                            binding.btnCantResolve.visibility = View.GONE
                            statusImage.setImageResource(R.drawable.sign_resolved)
                            statusImage.visibility = View.VISIBLE
                            binding.txtTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                            dueDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                            daysLeft.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                            status.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                        }
                        Task.CANT_RESOLVE -> {
                            binding.btnResolve.visibility = View.GONE
                            binding.btnCantResolve.visibility = View.GONE
                            statusImage.setImageResource(R.drawable.unresolved_sign)
                            statusImage.visibility = View.VISIBLE
                            binding.txtTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_text))
                            dueDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_text))
                            daysLeft.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_text))
                            status.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_text))
                        }
                    }
                }
            }
        }

        setBg("#15897b", binding.btnResolve)
        setBg("#EF4B5E", binding.btnCantResolve)

        binding.btnResolve.setOnClickListener {
            val addCommentFragment = AddTaskCommentAndResolve()

            if (addCommentFragment.isAdded) {
                addCommentFragment.dismiss()
            }

            addCommentFragment.show(childFragmentManager, AddComment.TAG)
        }

        binding.btnCantResolve.setOnClickListener {
            val addCommentFragment = AddTaskCommentAndCantResolve()

            if (addCommentFragment.isAdded) {
                addCommentFragment.dismiss()
            }

            addCommentFragment.show(childFragmentManager, AddComment.TAG)
        }
    }

    fun markTaskResolved(comment: String? = null){
       comment?.let {
            vm.resolveTaskWithComment(taskId, Task.RESOLVED, it)
        } ?: run {
            vm.resolveTask(taskId, Task.RESOLVED)
       }
    }

    fun markTaskCantResolve(comment: String? = null){
        comment?.let {
            vm.resolveTaskWithComment(taskId, Task.CANT_RESOLVE, it)
        } ?: run {
            vm.resolveTask(taskId, Task.CANT_RESOLVE)
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