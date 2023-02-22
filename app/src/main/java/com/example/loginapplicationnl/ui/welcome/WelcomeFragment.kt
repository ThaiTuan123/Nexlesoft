package com.example.loginapplicationnl.ui.welcome

import android.view.Gravity
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.example.loginapplicationnl.R
import com.example.loginapplicationnl.base.BaseFragment
import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, WelcomeViewModel>() {
    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentWelcomeBinding.inflate(inflater)

    override fun setUpView() {
        val res = arguments?.getParcelable("LoginResponse") as? LoginResponses
        viewBinding.txtDisplayName.text = res?.displayName
    }

    override fun registerLiveData() {

    }

    override fun registerEvent() {
        viewBinding.toolbar.setNavigationOnClickListener {
            viewBinding.drawerLayout.openDrawer(Gravity.LEFT)
        }
        viewBinding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemLogout -> {
                    findNavController().navigateUp()
                }
            }
            true
        }
    }
}