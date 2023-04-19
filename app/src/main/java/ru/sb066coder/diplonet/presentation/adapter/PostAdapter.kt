package ru.sb066coder.diplonet.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.CardPostBinding
import ru.sb066coder.diplonet.domain.dto.Post

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {


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
            tvPublished.text = post.published
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

