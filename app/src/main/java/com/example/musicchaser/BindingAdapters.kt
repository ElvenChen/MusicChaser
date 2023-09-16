package com.example.musicchaser

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


////////// Profile Page //////////
@BindingAdapter("getUserNickname")
fun getUserNickname(textView: TextView, userNickname: String) {
    textView.text = "暱稱：$userNickname"
}

@BindingAdapter("getUserAccount")
fun getUserAccount(textView: TextView, userAccount: String) {
    textView.text = "使用者帳號：$userAccount"
}

////////// Event Page //////////
@BindingAdapter("bindImage")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}