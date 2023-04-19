package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import ru.sb066coder.diplonet.databinding.FragmentWallBinding
import ru.sb066coder.diplonet.presentation.adapter.PostAdapter
import ru.sb066coder.diplonet.presentation.viewmodel.PostViewModel

/**
 * Экран, на котором показывается список постов
 * */
class WallFragment : Fragment() {

    val viewModel = PostViewModel()
    private lateinit var binding: FragmentWallBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        val adapter = PostAdapter()
        binding.rvPosts.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("WallFragment", it.toString())
        }
    }
}