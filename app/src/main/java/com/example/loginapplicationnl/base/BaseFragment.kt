package com.example.loginapplicationnl.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.example.loginapplicationnl.dialogManager.DialogManager
import com.example.loginapplicationnl.dialogManager.DialogManagerImpl

abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel> : Fragment() {
    private var _viewBinding: V? = null
    protected val viewBinding get() = _viewBinding!!

    var dialogManager: DialogManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogManager = DialogManagerImpl(getContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _viewBinding = inflateViewBinding(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        registerLiveData()
    }

    fun showLoading() {
        dialogManager?.showLoading()
    }

    fun hideLoading() {
        dialogManager?.hideLoading()
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater): V

    abstract fun setUpView()

    open fun registerLiveData() {
//        }
    }

    /**
     * Toast shown for short period of time
     * Toast.LENGTH_SHORT - Toast delay 2000 ms predefined
     *
     * @param message - String
     */
    protected open fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Toast shown for short period of time
     * Toast.LENGTH_SHORT - Toast delay 2000 ms predefined
     *
     * @param message - The message follow int
     */
    protected open fun showToast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
