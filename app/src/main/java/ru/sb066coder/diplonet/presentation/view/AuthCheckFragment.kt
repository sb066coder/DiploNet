package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.FragmentAuthCheckBinding
import ru.sb066coder.diplonet.presentation.viewmodel.AuthViewModel

@AndroidEntryPoint
class AuthCheckFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()

    private var _binding: FragmentAuthCheckBinding? = null
    private val binding: FragmentAuthCheckBinding
        get() = _binding ?: throw RuntimeException("FragmentAuthCheckBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.data.observe(viewLifecycleOwner) {
            findNavController().navigate(
                if (viewModel.authenticated) {
                    R.id.action_authCheckFragment_to_wallFragment
                } else {
                    R.id.action_authCheckFragment_to_welcomeFragment
                }
            )
        }
    }
}