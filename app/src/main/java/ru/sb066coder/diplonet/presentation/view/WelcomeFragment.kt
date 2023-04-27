package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.sb066coder.diplonet.databinding.FragmentWelcomeBinding
import ru.sb066coder.diplonet.presentation.viewmodel.AuthViewModel

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    private val viewModel: AuthViewModel by viewModels(::requireParentFragment)

    private var mode: Mode = Mode.SIGN_IN_MODE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            when (mode) {
                Mode.SIGN_IN_MODE -> {
                    binding.grSignUp.visibility = View.VISIBLE
                    mode = Mode.SIGN_UP_MODE
                }
                Mode.SIGN_UP_MODE -> TODO()
            }
        }
        binding.btnSignIn.setOnClickListener {
            when (mode) {
                Mode.SIGN_IN_MODE -> TODO()
                Mode.SIGN_UP_MODE -> {
                    binding.grSignUp.visibility = View.GONE
                    mode = Mode.SIGN_IN_MODE
                }
            }
        }
    }
}

enum class Mode {
    SIGN_IN_MODE, SIGN_UP_MODE
}
