package com.atakavuncu.booktime.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.repository.UserRepository

class ProfileFragment : Fragment() {

    private lateinit var usernameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        usernameTextView = view.findViewById(R.id.usernameTextView)

        val args = arguments
        var username = args?.getString("username")
        if (args != null && args.containsKey("username")){
            username = args.getString("username") ?: "gelmedi"
        }
        usernameTextView.text = username



        return view
    }
}