package com.example.newsapp.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.utilities.Resource
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.views.MainActivity
import com.example.newsapp.views.adapter.NewsAdapter

class HomeFragment : Fragment() {
lateinit var viewModel: NewsViewModel
lateinit var newsAdapter: NewsAdapter
lateinit var binding: FragmentHomeBinding
    private  val TAG = "HomeFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel=(activity as MainActivity).viewModel
        setUpRecyclerView()
        viewModel.allNews.observe(viewLifecycleOwner, Observer {
            response -> when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.response?.results)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.data?.let { message ->
                        Log.e(TAG, "An error occurred : $message")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
        }
        })
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }    private fun showProgressBar() {
        binding.paginationProgressBar.visibility=View.VISIBLE
    }

    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
            }
    }
}