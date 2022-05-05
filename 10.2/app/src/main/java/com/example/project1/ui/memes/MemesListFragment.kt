package com.example.project1.ui.memes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.base.ParentFragment
import com.example.project1.data.models.DefaultMemeInfo
import com.example.project1.ui.history.HistoryAdapter
import org.koin.android.ext.android.inject

class MemesListFragment : ParentFragment() {

    private val viewModel: MemesViewModel by inject()

    private lateinit var rvMemes: RecyclerView

    private val memesAdapter by lazy {
        MemesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_memes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()
    }

    override fun bindViews(view: View) = with(view) {
        rvMemes = findViewById(R.id.rvMemesList)

        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvMemes.layoutManager = linearLayoutManager
    }

    override fun setData() {
        observeViewModel()
    }

    private fun setAdapter() {
        rvMemes.adapter = memesAdapter
    }

    var list: List<DefaultMemeInfo>? = null
    private fun observeViewModel() {
        if(list == null){
            viewModel.getMemesDefaultList()
        }else{
            memesAdapter.addItems(list!!)
        }

        viewModel.list.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is MemesViewModel.State.ShowLoading -> {
                    }
                    is MemesViewModel.State.ResultDefaultMeme -> {
                        it.memesInfo?.data?.memes?.let { it1 ->
                            list = it1
                            memesAdapter.addItems(it1)
                        }
                    }
                    is MemesViewModel.State.Error -> {
                    }
                }
            })
    }

}