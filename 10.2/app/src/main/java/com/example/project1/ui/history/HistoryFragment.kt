package com.example.project1.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.base.ParentFragment
import com.example.project1.ui.main.MainViewModel
import org.koin.android.ext.android.inject

class HistoryFragment : ParentFragment() {

    private val viewModel: MainViewModel by inject()

    private lateinit var rvHistory: RecyclerView
    private lateinit var btnClear: Button

    private val historyAdapter by lazy {
        HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()
    }

    override fun bindViews(view: View) = with(view) {
        rvHistory = findViewById(R.id.rvMemesList)
        btnClear = view.findViewById(R.id.btn_clear)

        btnClear.setOnClickListener{
            viewModel.clearHistory()
            historyAdapter.clearAll()
        }

        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvHistory.layoutManager = linearLayoutManager
        }

    override fun setData() {
        observeViewModel()
    }

    private fun setAdapter() {
        rvHistory.adapter = historyAdapter
    }

    private fun observeViewModel() {
        viewModel.readAllData.observe(viewLifecycleOwner,
            Observer {
                historyAdapter.addItems(it)
            })
    }

}