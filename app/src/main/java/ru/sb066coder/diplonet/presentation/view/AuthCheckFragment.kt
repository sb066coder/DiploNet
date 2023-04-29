package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.FragmentAuthCheckBinding
import ru.sb066coder.diplonet.presentation.viewmodel.AuthViewModel

class AuthCheckFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels(::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAuthCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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