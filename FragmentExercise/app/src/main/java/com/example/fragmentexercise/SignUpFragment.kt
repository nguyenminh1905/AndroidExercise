package com.example.fragmentexercise

import android.content.Context
import android.media.MediaCodec.MetricsConstants.MODE
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.w3c.dom.Text


class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val forgot = view.findViewById<TextView>(R.id.txt_forgot_signup)
        forgot.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_forgetPassFragment)
        }

        val logIn = view.findViewById<TextView>(R.id.txt_log_in)
        logIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        val emailInput = view.findViewById<EditText>(R.id.edit_email)
        val passwordInput = view.findViewById<EditText>(R.id.edit_pass)
        val repasswordInput = view.findViewById<EditText>(R.id.edit_repass)
        val signUpBT = view.findViewById<Button>(R.id.bt_signup)
        val sharedPref = requireActivity().getSharedPreferences("Credentials", Context.MODE_PRIVATE)

        //Not work with assigning text to Input?
        signUpBT.setOnClickListener {
            if (isValidEmail(emailInput.text.toString()) && isValidPassword(passwordInput.text.toString()) && passwordInput.text.toString() == repasswordInput.text.toString()) {
                with(sharedPref.edit()){
                    putString("email", emailInput.text.toString())
                    putString("password", passwordInput.text.toString())
                    apply()
                }
                findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
            } else {
                Toast.makeText(context, "Invalid email or password.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}