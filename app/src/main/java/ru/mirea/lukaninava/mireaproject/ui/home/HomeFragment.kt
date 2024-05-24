package ru.mirea.lukaninava.mireaproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import ru.mirea.lukaninava.mireaproject.Firebase
import ru.mirea.lukaninava.mireaproject.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val textView: TextView = binding.textHome
        textView.text = currentUser?.email ?: "No email"

        binding.button.setOnClickListener {
            signOut()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(activity, Firebase::class.java)
        intent.putExtra("userState", "signedOut")
        startActivity(intent)
        activity?.finish()
    }
}
