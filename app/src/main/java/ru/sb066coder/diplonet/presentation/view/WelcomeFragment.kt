package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.FragmentWelcomeBinding
import ru.sb066coder.diplonet.presentation.util.AndroidUtils
import ru.sb066coder.diplonet.presentation.util.TextWatcherImpl
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
                Mode.SIGN_IN_MODE -> {
                    viewModel.signIn(
                        binding.etLogin.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    AndroidUtils.hideKeyboard(requireView())
                }
                Mode.SIGN_UP_MODE -> {
                    binding.grSignUp.visibility = View.GONE
                    mode = Mode.SIGN_IN_MODE
                }
            }
        }
        binding.etLogin.addTextChangedListener(TextWatcherImpl(LOGIN_FIELD_NAME) {
            viewModel.cancelInputError(it)
        })
        binding.etPassword.addTextChangedListener(TextWatcherImpl(PASSWORD_FIELD_NAME) {
            viewModel.cancelInputError(it)
        })
        viewModel.authDone.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.authCheckFragment)
        }
        viewModel.errorLogin.observe(viewLifecycleOwner) {
            binding.tilLogin.error = if (it) getString(R.string.error_login) else null
        }
        viewModel.errorPassword.observe(viewLifecycleOwner) {
            binding.tilPassword.error = if (it) getString(R.string.error_password) else null
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        enum class Mode {
            SIGN_IN_MODE, SIGN_UP_MODE
        }
        const val LOGIN_FIELD_NAME = "etLogin"
        const val PASSWORD_FIELD_NAME = "etPassword"
}


}
