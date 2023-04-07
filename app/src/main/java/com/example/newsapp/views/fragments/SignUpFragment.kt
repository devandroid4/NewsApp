package com.example.newsapp.views.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSignUpBinding
import com.example.newsapp.localDb.AppSessionManager
import com.example.newsapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import java.util.*


class SignUpFragment : Fragment() {
    private lateinit var binding:FragmentSignUpBinding
    var appSessionManager: AppSessionManager? = null
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
                    showFragment(LoginFragment.newInstance())
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        appSessionManager = AppSessionManager(requireContext())


        appSessionManager?.storeRegInfo("")

        binding.signUpBtn.setOnClickListener(View.OnClickListener {
            verifyData()
        })
        binding.signInText.setOnClickListener {
            showFragment(LoginFragment.newInstance())
        }



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SignUpFragment().apply {
            }
    }
    private fun verifyData() {
        if (binding.emailEditSignUp.text.isEmpty()&&binding.passEditSignUp.text.isEmpty()) {
            binding.emailEditSignUp.error = "Field is empty!"
            binding.passEditSignUp.error = "Field is empty!"
            return
        }
        viewModel?.register(binding.emailEditSignUp.text.toString(),binding.passEditSignUp.text.toString())

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

}