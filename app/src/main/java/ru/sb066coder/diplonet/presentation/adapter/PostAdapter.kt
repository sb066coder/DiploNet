package ru.sb066coder.diplonet.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.CardPostBinding
import ru.sb066coder.diplonet.domain.dto.Attachment
import ru.sb066coder.diplonet.domain.dto.Post
import ru.sb066coder.diplonet.presentation.PostInteractionListener
import ru.sb066coder.diplonet.presentation.util.ViewUtil

class PostAdapter(
    private val postInteractionListener: PostInteractionListener
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        // Author avatar downloading
        Glide.with(holder.binding.ivAuthorAvatar)
            .load(post.authorAvatar)
            .timeout(10_000)
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_24)
            .into(holder.binding.ivAuthorAvatar)
        with(holder.binding) {
            tvContent.text = post.content
            tvAuthorName.text = post.author
            tvPublished.text = ViewUtil.formatDate(post.published)
            // Show attachment icon
            if (post.attachment != null) {
                grAttachment.visibility = View.VISIBLE
                ivAttachment.setImageResource(
                    when (post.attachment.type) {
                        Attachment.Companion.AttachmentType.IMAGE -> R.drawable.ic_image_attachment
                        Attachment.Companion.AttachmentType.AUDIO -> R.drawable.ic_audio_attachment
                        Attachment.Companion.AttachmentType.VIDEO -> R.drawable.ic_video_attachment
                    }
                )
            } else {
                grAttachment.visibility = View.INVISIBLE
            }
            // Show like icon
            ivLike.setImageResource(if (post.likedByMe) {
                R.drawable.ic_heart_filled
            } else {
                R.drawable.ic_heart_border
            })
            tvAmountOfLikes.text = post.likeOwnerIds.size.toString()
            ivLike.setOnClickListener {
                postInteractionListener.onLikeClick(post.id, post.likedByMe)
            }
            root.setOnClickListener {
                postInteractionListener.onItemClick(post.id)
            }
        }
    }

    class PostViewHolder(val binding: CardPostBinding) : ViewHolder(binding.root)

    class PostDiffCallback : ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }
}

