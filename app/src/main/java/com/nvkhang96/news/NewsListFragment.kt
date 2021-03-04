package com.nvkhang96.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nvkhang96.news.adapters.NewsAdapter
import com.nvkhang96.news.databinding.FragmentNewsListBinding
import com.nvkhang96.news.viewmodels.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment: Fragment() {

    private val adapter = NewsAdapter()
    private val viewModel: NewsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsListBinding.inflate(inflater, container, false)
            .apply {
                newsList.adapter = adapter

                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_refresh -> {
                            swipeRefresh.isRefreshing = true
                            viewModel.fetchLatestNews()
                            true
                        }
                        R.id.action_settings -> {
                            val direction = NewsListFragmentDirections.actionNewsListFragmentToSettingsFragment()
                            findNavController().navigate(direction)
                            true
                        }
                        else -> false
                    }
                }

                swipeRefresh.setOnRefreshListener {
                    viewModel.fetchLatestNews()
                }
            }

        viewModel.newsList.observe(viewLifecycleOwner) {
            it.channel?.let { channel ->
                adapter.submitList(channel.itemList)
                binding.newsList.smoothScrollToPosition(0)
                binding.swipeRefresh.isRefreshing = false
//                Toast.makeText(requireContext(), "Latest news loaded", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}