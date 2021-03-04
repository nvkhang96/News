package com.nvkhang96.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nvkhang96.news.NewsListFragment
import com.nvkhang96.news.NewsListFragmentDirections
import com.nvkhang96.news.data.News
import com.nvkhang96.news.data.VnexpressRssItem
import com.nvkhang96.news.databinding.ListItemNewsBinding

class NewsAdapter: ListAdapter<VnexpressRssItem, RecyclerView.ViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            ListItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        (holder as NewsViewHolder).bind(news)
    }

    class NewsViewHolder(
        private val binding: ListItemNewsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.news?.let { news ->
                    navigationToWebView(news.link, it)
                }
            }
        }

        private fun navigationToWebView(link: String, view: View) {
            val direction = NewsListFragmentDirections.actionNewsListFragmentToWebViewFragment(link)
            view.findNavController().navigate(direction)
        }

        fun bind(item: VnexpressRssItem) {
            binding.apply {
                news = item
                executePendingBindings()
            }
        }
    }
}

private class NewsDiffCallback : DiffUtil.ItemCallback<VnexpressRssItem>() {

    override fun areItemsTheSame(oldItem: VnexpressRssItem, newItem: VnexpressRssItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VnexpressRssItem, newItem: VnexpressRssItem): Boolean {
        return  oldItem == newItem
    }
}