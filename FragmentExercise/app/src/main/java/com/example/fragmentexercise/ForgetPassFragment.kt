package com.example.fragmentexercise

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController


class ForgetPassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reSend = view.findViewById<Button>(R.id.bt_resend)
        val countDown = view.findViewById<TextView>(R.id.countdown)
        reSend.setOnClickListener{
            val timer = object : CountDownTimer(30000, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    val secondsUntilFinished = millisUntilFinished / 1000
                    countDown.text = "Wait $secondsUntilFinished seconds before sending it"
                }

                override fun onFinish() {
                    countDown.text = "Wait 0 seconds before sending it"
                    findNavController().navigate(R.id.action_forgetPassFragment_to_forgetPopUpFragment)
                }
            }
            timer.start()
        }
    }
}