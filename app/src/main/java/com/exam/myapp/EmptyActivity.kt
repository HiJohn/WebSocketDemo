package com.exam.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exam.myapp.databinding.ActivityEmptyBinding

class EmptyActivity : AppCompatActivity() {


    private val binding: ActivityEmptyBinding by lazy {
        ActivityEmptyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}