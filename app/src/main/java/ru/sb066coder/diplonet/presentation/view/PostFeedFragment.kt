package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sb066coder.diplonet.databinding.FragmentPostFeedBinding
import ru.sb066coder.diplonet.presentation.PostInteractionListener
import ru.sb066coder.diplonet.presentation.adapter.PostAdapter
import ru.sb066coder.diplonet.presentation.viewmodel.PostFeedViewModel

/**
 * Screen which the roll of posts is shown on
 * */

@AndroidEntryPoint
class PostFeedFragment : Fragment() {

    private val viewModel : PostFeedViewModel by viewModels()
    private var _binding: FragmentPostFeedBinding? = null
    private val binding: FragmentPostFeedBinding
        get() = _binding ?: throw RuntimeException("FragmentPostFeedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostFeedBinding.inflate(
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
        //viewModel.getData()
        val adapter = PostAdapter(object : PostInteractionListener {
            override fun onLikeClick(id: Int, likedByMe: Boolean) {
                Log.i("PostAdapter", "onLikeClicked id $id")
                viewModel.likePostById(id, likedByMe)
            }
            override fun onItemClick(id: Int) {
                Log.i("PostAdapter", "onItemClicked id $id")
                findNavController().navigate(
                    PostFeedFragmentDirections.actionPostFeedFragmentToOpenPostFragment(id)
                )
            }
        })
        binding.rvPosts.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.data.collectLatest {
                    adapter.submitData(it)
                    Log.d("WallFragment", it.toString())
                }
            }
        }
    }
}