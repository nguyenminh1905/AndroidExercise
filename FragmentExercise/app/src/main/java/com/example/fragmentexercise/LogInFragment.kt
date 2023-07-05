package com.example.fragmentexercise

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class LogInFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val forgot = view.findViewById<TextView>(R.id.txt_forgot)
        forgot.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgetPassFragment)
        }
        val signUp = view.findViewById<TextView>(R.id.txt_sign_up)
        signUp.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
        val sharedPref = requireActivity().getSharedPreferences("Credentials", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        val password = sharedPref.getString("password", "")
        val emailEditText = view.findViewById<EditText>(R.id.edit_email)
        emailEditText.setText(email)
        val passwordEditText = view.findViewById<EditText>(R.id.edit_pass)
        passwordEditText.setText(password)
        val logInBT = view.findViewById<Button>(R.id.bt_login)

        logInBT.setOnClickListener {
            if (emailEditText.text.toString() == email && passwordEditText.text.toString() == password) {
                findNavController().navigate(R.id.action_logInFragment_to_appFragment)
            } else {
                Toast.makeText(
                    context,
                    "Incorrect email or password. Try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //editText not clear when exit app
    override fun onDestroy() {
        super.onDestroy()
        val emailEditText = view?.findViewById<EditText>(R.id.edit_email)
        emailEditText?.setText("")
        val passwordEditText = view?.findViewById<EditText>(R.id.edit_pass)
        passwordEditText?.setText("")
    }
}