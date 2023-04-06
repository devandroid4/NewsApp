package com.example.newsapp.views.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSignUpBinding
import com.example.newsapp.localDb.AppSessionManager
import com.example.newsapp.utilities.CheckInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class SignUpFragment : Fragment() {
    private lateinit var binding:FragmentSignUpBinding
    private var date: DatePickerDialog.OnDateSetListener? = null
    private lateinit var myCalendar: Calendar
    private lateinit var dialog: Dialog
    var appSessionManager: AppSessionManager? = null

    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    private var isShowPass = false
    var registerMap = java.util.HashMap<String, Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        appSessionManager = AppSessionManager(requireContext())


        appSessionManager?.storeRegInfo("")

        binding.createNewAc.setOnClickListener(View.OnClickListener {
            verifyData()
        })

        binding.backBtn.setOnClickListener {
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
        if (TextUtils.isEmpty(binding.firstName.text)) {
            binding.firstName.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.lastName.text)) {
            binding.lastName.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.etEmail.text)) {
            binding.etEmail.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.etPassword.text)) {
            binding.etPassword.error = "Field is empty!"
            return
        }
        binding.idTypeRG.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId) as RadioButton
            val gender = radioButton.text.toString()
            Log.d("TAG", "onCheckedChanged: $gender")
            registerMap["gender"] = gender
        }

        with(registerMap) {
            put("email",binding.etEmail.text.toString())
            put("password",binding.etPassword.text.toString())
            put("first_name",binding.firstName.text.toString())
            put("last_name",binding.lastName.text.toString())
            val selectedOption: Int = binding.idTypeRG.checkedRadioButtonId
            radioButton = requireActivity().findViewById(selectedOption)
            Log.d("TAG", "verifyData: "+radioButton.text)
            put("gender",radioButton.text)

        }



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