package com.example.loginapplicationnl.ui.welcome

import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.example.loginapplicationnl.base.BaseFragment
import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, WelcomeViewModel>() {
    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentWelcomeBinding.inflate(inflater)

    override fun setUpView() {

        val res = arguments?.getParcelable("LoginResponse") as? LoginResponses
        viewBinding.txtHeader1.text = res?.firstName

        viewBinding.imageView.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}