package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.FragmentOpenPostBinding
import ru.sb066coder.diplonet.domain.dto.Attachment
import ru.sb066coder.diplonet.domain.dto.Post
import ru.sb066coder.diplonet.presentation.viewmodel.OpenPostViewModel

/**
 * Screen which the post is shown on
 * */

@AndroidEntryPoint
class OpenPostFragment: Fragment() {

    private val args by navArgs<OpenPostFragmentArgs>()

    private val viewModel: OpenPostViewModel by viewModels()

    private var _binding: FragmentOpenPostBinding? = null
    private val binding: FragmentOpenPostBinding
        get() = _binding ?: throw RuntimeException("FragmentOpenPostBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenPostBinding.inflate(
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
        fillFields()
    }

    private fun fillFields() {
        val post = viewModel.getPostById(args.id)
        // Author avatar downloading
        setImage(binding.ivAuthorAvatar, post.authorAvatar)
        with(binding) {
            tvContent.text = post.content
            tvAuthorName.text = post.author
            tvPublished.text = ru.sb066coder.diplonet.presentation.util.ViewUtil.formatDate(post.published)
            // Show attachment content
            when (post.attachment?.type) {
                Attachment.Companion.AttachmentType.IMAGE -> {
                    binding.ivAttachment.visibility = View.VISIBLE
                    setImage(binding.ivAttachment, post.attachment.url)
                }
                Attachment.Companion.AttachmentType.VIDEO -> {} //TODO: Realize attachment play
                Attachment.Companion.AttachmentType.AUDIO -> {} //TODO: Realize attachment play
                null -> {}
            }
            // Show like icon
            ivLike.setImageResource(if (post.likedByMe) {
                R.drawable.ic_heart_filled
            } else {
                R.drawable.ic_heart_border
            })
            tvAmountOfLikes.text = post.likeOwnerIds.size.toString()
            ivLike.setOnClickListener {
                viewModel.likePostById(post.id, post.likedByMe)
            }
        }
    }

    private fun setImage(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url)
            .timeout(10_000)
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_24)
            .into(view)
    }
}