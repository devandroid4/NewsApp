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
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.localDb.AppSessionManager
import com.example.newsapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    var signInMap = HashMap<String, Any>()
    lateinit  var appSessionManager: AppSessionManager
    private var viewModel: AuthViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[AuthViewModel::class.java]
        viewModel!!.userData.observe(this,
            androidx.lifecycle.Observer<FirebaseUser?> { firebaseUser ->
                if (firebaseUser != null) {
                    //showFragment(SignUpFragment.newInstance())
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

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
        if (email.isEmpty()&&password.isEmpty()) {
            binding.etEmail.error = "Field is empty!"
            binding.etPassword.error = "Field is empty!"
            return
        }
        viewModel?.signIn(email,password)
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