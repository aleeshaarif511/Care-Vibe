package com.example.wellcheck.Activity
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wellcheck.Adapter.TopDoctorAdapter
import com.example.wellcheck.Adapter.TopDoctorAdapter2

import com.example.wellcheck.ViewModel.MainViewModel
import com.example.wellcheck.databinding.ActivityTopDoctorsBinding
class TopDoctorsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityTopDoctorsBinding
    private val viewModel=MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTopDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        innitTopDoctors()
    }
    private fun innitTopDoctors() {
    binding.apply {
        progressBarTopDoc.visibility= View.VISIBLE
        viewModel.doc.observe(this@TopDoctorsActivity, Observer{
            viewTopDoctorList.layoutManager=
                LinearLayoutManager(this@TopDoctorsActivity, LinearLayoutManager.VERTICAL,false)
            viewTopDoctorList.adapter= TopDoctorAdapter2(it)
            progressBarTopDoc.visibility= View.GONE
        })
        viewModel.loadDoctors()
        backBtn.setOnClickListener { finish() }
    }
}

}
