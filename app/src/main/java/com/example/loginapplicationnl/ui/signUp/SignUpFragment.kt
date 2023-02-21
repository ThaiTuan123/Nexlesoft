package com.example.loginapplicationnl.ui.signUp

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.loginapplicationnl.base.BaseFragment
import com.example.loginapplicationnl.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {
    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentSignUpBinding.inflate(inflater)

    override fun setUpView() {
        signIn()
    }

    override fun registerLiveData() {
        //TODO("Not yet implemented")
    }

    override fun registerEvent() {
        //TODO("Not yet implemented")
    }

    private fun signIn() {
        viewBinding.txtSignIn.setOnClickListener() {
            findNavController().navigateUp()
        }
    }
}