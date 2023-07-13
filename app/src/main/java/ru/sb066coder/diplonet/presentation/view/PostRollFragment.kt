package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sb066coder.diplonet.databinding.FragmentPostRollBinding
import ru.sb066coder.diplonet.domain.dto.Post
import ru.sb066coder.diplonet.presentation.PostInteractionListener
import ru.sb066coder.diplonet.presentation.adapter.PostAdapter
import ru.sb066coder.diplonet.presentation.viewmodel.PostRollViewModel

/**
 * Screen which the roll of posts is shown on
 * */

@AndroidEntryPoint
class PostRollFragment : Fragment() {

    private val viewModel : PostRollViewModel by viewModels()
    private var _binding: FragmentPostRollBinding? = null
    private val binding: FragmentPostRollBinding
        get() = _binding ?: throw RuntimeException("FragmentWallBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostRollBinding.inflate(
            inflater, container, false
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
                findNavController().navigate(
                    PostRollFragmentDirections.actionPostRollFragmentToOpenPostFragment(id)
                )
            }
        })
        binding.rvPosts.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("WallFragment", it.toString())
        }
    }
}