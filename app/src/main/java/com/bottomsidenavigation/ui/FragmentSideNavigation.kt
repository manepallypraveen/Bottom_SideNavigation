package com.bottomsidenavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottomsidenavigation.DashWithSideBottomNaviagtionActivity
import com.bottomsidenavigation.R
import com.bottomsidenavigation.databinding.FragmentSidenavigationBinding

class FragmentSideNavigation : Fragment(), View.OnClickListener {

    private var _binding: FragmentSidenavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSidenavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLogout.setOnClickListener(this)
        binding.closeDrawer.setOnClickListener(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.close_drawer -> {
                (activity as DashWithSideBottomNaviagtionActivity).hanldeSideNavigationClicks(
                    resources.getString(
                        R.string.close_drawer
                    )
                )
            }
            R.id.layout_logout -> {
                (activity as DashWithSideBottomNaviagtionActivity).hanldeSideNavigationClicks(
                    resources.getString(
                        R.string.logout
                    )
                )
            }

        }
    }

}