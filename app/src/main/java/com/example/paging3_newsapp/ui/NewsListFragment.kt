package com.example.paging3_newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.paging3_newsapp.adapters.NewsPagingAdapter
import com.example.paging3_newsapp.databinding.FragmentNewsListBinding
import com.example.paging3_newsapp.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private lateinit var _binding: FragmentNewsListBinding
    private val binding get() = _binding!!

    private lateinit var myPagingAdapter: NewsPagingAdapter
    private val myViewModel: MyViewModel by viewModels()

    @OptIn(ExperimentalPagingApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            myViewModel.pager.collect {
                myPagingAdapter.submitData(it)
                Log.d("articles","  Hello")
            }
        }

        return binding.root
    }

    private fun setUpRecyclerView(){
        myPagingAdapter = NewsPagingAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = myPagingAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}