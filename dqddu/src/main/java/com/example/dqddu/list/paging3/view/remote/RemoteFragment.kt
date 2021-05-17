package com.example.dqddu.list.paging3.view.remote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dqddu.R
import com.example.dqddu.base.viewBinding
import com.example.dqddu.databinding.FragmentPaging3RemoteBinding
import com.example.dqddu.list.paging3.view.loader.adapter.LoaderStateAdapter
import com.example.dqddu.list.paging3.view.remote.adapter.RemoteDogImageAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class RemoteFragment : Fragment(R.layout.fragment_paging3_remote) {

    private val binding: FragmentPaging3RemoteBinding by viewBinding(FragmentPaging3RemoteBinding::bind)
    private val viewModel: RemoteViewModel by viewModels()
    private lateinit var adapter: RemoteDogImageAdapter
    private lateinit var loaderStateAdapter: LoaderStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews()
        fetchDogImages()
    }

    private fun initMembers() {
        adapter = RemoteDogImageAdapter()
        loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
    }

    private fun setUpViews() {
        binding.recyclerDogImages.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    private fun fetchDogImages() {
        lifecycleScope.launch {
            viewModel.fetchDogImages().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
