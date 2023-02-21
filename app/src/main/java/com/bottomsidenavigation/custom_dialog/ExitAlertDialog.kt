package com.bottomsidenavigation.custom_dialog

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bottomsidenavigation.DashWithSideBottomNaviagtionActivity
import com.bottomsidenavigation.R

class ExitAlertDialog : DialogFragment() {

    var from = ""

    companion object {
        const val TAG = "SimpleDialog"
        private const val KEY_from = "from"

        fun newInstance(customerId: String, context: Context): ExitAlertDialog {
            val args = Bundle()
            args.putString(KEY_from, customerId)
            val fragment = ExitAlertDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            from = it.getString(KEY_from)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val window = dialog!!.window
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // This flag is required to set otherwise the setDimAmount method will not show any effect
            window.setDimAmount(0.5f) //0 for no dim to 1 for full dim
        }
        return inflater.inflate(R.layout.dialog_exit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var btnYes = view.findViewById(R.id.btnYes) as TextView
        var btnNo = view.findViewById(R.id.btnNo) as TextView
        btnYes.setOnClickListener {
            dialog?.dismiss()
            handleFinishApp()
        }
        btnNo.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
//        val height = displayMetrics.heightPixels
        dialog?.window?.setLayout(width - 128, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.setCanceledOnTouchOutside(false)
    }

    fun handleFinishApp() {
        if (from.equals("DashWithSideBottomNaviagtionActivity")) {
            (activity as DashWithSideBottomNaviagtionActivity).closeApp()
        }
    }

}