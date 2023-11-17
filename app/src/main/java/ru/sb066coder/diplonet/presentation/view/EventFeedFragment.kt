package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.sb066coder.diplonet.databinding.FragmentEventFeedBinding

class EventFeedFragment: Fragment() {

    private var _binding : FragmentEventFeedBinding? = null
    private val binding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentEventFeedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventFeedBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}