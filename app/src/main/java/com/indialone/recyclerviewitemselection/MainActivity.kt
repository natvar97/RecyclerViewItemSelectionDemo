package com.indialone.recyclerviewitemselection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: MyAdapter
    private lateinit var rv: RecyclerView
    private lateinit var tv_data_not_found: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_data_not_found = findViewById(R.id.tv_data_not_found)

        setUpRecyclerView()

    }

    fun visibleTvDataNotFound() {
        tv_data_not_found.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        rv = findViewById(R.id.rv)
        mAdapter = MyAdapter(this, getArrayList())
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = mAdapter
    }

    private fun getArrayList(): ArrayList<ListItem> {
        return arrayListOf(
            ListItem(R.drawable.image1, "Image1"),
            ListItem(R.drawable.image2, "Image2"),
            ListItem(R.drawable.image3, "Image3"),
            ListItem(R.drawable.image4, "Image4"),
            ListItem(R.drawable.image5, "Image5"),
            ListItem(R.drawable.image6, "Image6"),
            ListItem(R.drawable.image7, "Image7"),
            ListItem(R.drawable.image8, "Image8"),
            ListItem(R.drawable.image9, "Image9"),
            ListItem(R.drawable.image10, "Image10"),
            ListItem(R.drawable.image11, "Image11"),
        )
    }

}