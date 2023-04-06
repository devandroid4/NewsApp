package com.example.newsapp.views.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.localDb.AppSessionManager

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    var signInMap = HashMap<String, Any>()
    lateinit  var appSessionManager: AppSessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login, container, false
        )
        appSessionManager = AppSessionManager(requireContext())
        binding.Login.setOnClickListener {
            login()
        }
        binding.createNewAc.setOnClickListener(View.OnClickListener {
            showFragment(SignUpFragment.newInstance())
        })

return binding.root

    }

    private fun login() {
        val email: String = binding.etEmail.text.toString()
        val password: String = binding.etPassword.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Field is empty!"
            return
        } else if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Password field is empty!"
            return
        }
        appSessionManager.setuserInfoAlldata(email)
        appSessionManager.setuserInfoAlldata(password)
        Toast.makeText(context,appSessionManager.getuserInfoAlldata(),Toast.LENGTH_LONG).show()
        showFragment(HomeFragment.newInstance())


    }
    private fun showFragment(fragment: Fragment?) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,  // enter
            R.anim.fade_out,  // exit
            R.anim.fade_in,  // popEnter
            R.anim.slide_out // popExit
        )
        fragmentTransaction.replace(R.id.fragment_container, fragment!!)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
            }
    }
}