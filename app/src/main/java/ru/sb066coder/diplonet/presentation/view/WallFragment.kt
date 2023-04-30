package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.sb066coder.diplonet.databinding.FragmentWallBinding
import ru.sb066coder.diplonet.presentation.PostInteractionListener
import ru.sb066coder.diplonet.presentation.adapter.PostAdapter
import ru.sb066coder.diplonet.presentation.viewmodel.PostViewModel

/**
 * Экран, на котором показывается список постов
 * */
class WallFragment : Fragment() {

    private val viewModel : PostViewModel by viewModels(::requireParentFragment)
    private var _binding: FragmentWallBinding? = null
    private val binding: FragmentWallBinding
        get() = _binding ?: throw RuntimeException("FragmentWallBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWallBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        val adapter = PostAdapter(object : PostInteractionListener {
            override fun onLikeClick(id: Int, likedByMe: Boolean) {
                Log.i("PostAdapter", "onLikeClicked id $id")
                viewModel.likePostById(id, likedByMe)
            }
            override fun onItemClick(id: Int) {
                Log.i("PostAdapter", "onItemClicked id $id")
            }
        })
        binding.rvPosts.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("WallFragment", it.toString())
        }
    }
}