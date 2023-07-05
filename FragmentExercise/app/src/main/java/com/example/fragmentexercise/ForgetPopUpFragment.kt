package com.example.fragmentexercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [ForgetPopUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgetPopUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forget_pop_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val signInBT = view.findViewById<Button>(R.id.forget_login)
        signInBT.setOnClickListener{
            findNavController().navigate(R.id.action_forgetPopUpFragment_to_logInFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}