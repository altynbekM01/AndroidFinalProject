package com.example.project1.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.project1.R
import com.example.project1.base.ParentFragment
import com.example.project1.data.models.CreateMeme
import com.example.project1.data.models.MemeData
import com.example.project1.data.models.MemeResponse
import com.example.project1.ui.memes.MemesViewModel
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.ext.android.inject

class MainFragment : ParentFragment() {

    private val viewModel: MainViewModel by inject()
    private val memesViewModel: MemesViewModel by inject()

    private lateinit var btnLoad: Button
    private lateinit var btnLike: ImageView
    private lateinit var ivMeme: ImageView

    private lateinit var etTemplate: TextInputEditText
    private lateinit var etText0: TextInputEditText
    private lateinit var etText1: TextInputEditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
    }

    override fun bindViews(view: View) = with(view) {
        etTemplate = view.findViewById(R.id.et_template_id)
        etText0 = view.findViewById(R.id.et_text0)
        etText1 = view.findViewById(R.id.et_text1)
        ivMeme = view.findViewById(R.id.iv_meme)

        btnLoad = view.findViewById(R.id.btn_load)
        btnLike = view.findViewById(R.id.btn_like)

        btnLike.setOnClickListener {
            btnLike.setColorFilter(Color.argb(255, 255, 0, 0))
            if (currentMemeResponse != null) {
                addToDatabase(currentMemeResponse!!.data)
            }
        }

        btnLoad.setOnClickListener {
            memesViewModel.createNewMeme(
                CreateMeme(
                    template_id = etTemplate.text.toString(),
                    text0 = etText0.text.toString(),
                    text1 = etText1.text.toString()
                )
            )
        }

    }

    override fun setData() {
        observeMemesViewModel()
    }

    var currentMemeResponse: MemeResponse? = null
    private fun addToDatabase(item: MemeData?) {
        if (item != null) {
            viewModel.addToDatabase(item)
        }
    }

    private fun observeMemesViewModel() {
        memesViewModel.liveData.observe(
            viewLifecycleOwner,
            Observer { result ->
                btnLike.setColorFilter(Color.argb(255, 0, 0, 0))
                when (result) {
                    is MemesViewModel.State.ShowLoading -> {
                    }
                    is MemesViewModel.State.Result -> {
                        Glide.with(this)
                            .load(result.memeResponse?.data?.url)
                            .into(ivMeme)
                        currentMemeResponse = result.memeResponse
                    }
                    is MemesViewModel.State.Error -> {
                    }
                }
            }
        )
    }

}