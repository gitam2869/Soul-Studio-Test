package com.app.soulstudio.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.soulstudio.R
import com.app.soulstudio.databinding.ActivityBookDetailsBinding
import com.app.soulstudio.model.dataclass.Items
import com.bumptech.glide.Glide

class BookDetailsActivity : AppCompatActivity() {

    private val TAG = "BookDetailsActivity"
    private lateinit var activityBookDetailsBinding: ActivityBookDetailsBinding

    private var items: Items? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityBookDetailsBinding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(activityBookDetailsBinding.root)

        if (intent != null) {
            items = intent.getParcelableExtra<Items>("itemData")
            setBookData()
        }
    }

    private fun setBookData() {
        activityBookDetailsBinding.run {
            if (items != null) {

                tvBookTitle.text = items!!.volumeInfo.title

                if (items!!.volumeInfo.description == null) {
                    tvBookDescription.text = "No description available"
                } else {
                    tvBookDescription.text = items!!.volumeInfo.description
                }


                if (items!!.volumeInfo.imageLinks != null) {
                    Glide.with(this@BookDetailsActivity)
                        .load(items!!.volumeInfo.imageLinks?.smallThumbnail)
                        .into(ivBookThumbnail)
                }
            }

        }
    }
}