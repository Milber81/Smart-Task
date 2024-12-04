package com.smart.task.ui.dailyDetails

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.task.R
import com.smart.task.databinding.AddCommentBinding

data class AddCommentStyling(val btnText: String, val btnColor: Int)

abstract class AddComment : BottomSheetDialogFragment() {

    abstract fun defineDismissCallback(callback: () -> Unit)

    abstract fun defineStyling(): AddCommentStyling

    private lateinit var binding: AddCommentBinding

    companion object {
        const val TAG = "AddComment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = AddCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bntApply.text = defineStyling().btnText
        binding.bntApply.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                defineStyling().btnColor
            )
        )

        binding.bntApply.setOnClickListener {
            dismiss()
            defineDismissCallback {}
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val styleResId: Int = R.style.ModalBottomSheetDialog

        setStyle(DialogFragment.STYLE_NORMAL, styleResId)

        return BottomSheetDialog(requireContext(), styleResId)
    }
}

class AddTaskCommentAndResolve : AddComment() {
    override fun defineDismissCallback(callback: () -> Unit) {
        return (parentFragment as TaskDetail).markTaskResolved()
    }

    override fun defineStyling(): AddCommentStyling =
        AddCommentStyling(getString(R.string.resolve), R.color.green)

}

class AddTaskCommentAndCantResolve : AddComment() {
    override fun defineDismissCallback(callback: () -> Unit) {
        return (parentFragment as TaskDetail).markTaskCantResolve()
    }

    override fun defineStyling(): AddCommentStyling =
        AddCommentStyling(getString(R.string.can_t_resolve), R.color.main_text)
}