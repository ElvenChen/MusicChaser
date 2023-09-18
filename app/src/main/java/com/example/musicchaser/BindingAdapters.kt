package com.example.musicchaser

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicchaser.ext.toCommentFormattedTime
import com.example.musicchaser.ext.toFormattedDay
import com.example.musicchaser.ext.toFormattedMonth
import com.example.musicchaser.ext.toFormattedTime


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
@BindingAdapter("getEventMonth")
fun getEventMonth(textView: TextView, eventDate: Long) {

    textView.text = eventDate.toFormattedMonth()
}

@BindingAdapter("getEventDay")
fun getEventDay(textView: TextView, eventDate: Long) {

    textView.text = eventDate.toFormattedDay()
}


////////// Event Detail Page //////////
@BindingAdapter("getEventAttendant")
fun getEventAttendant(textView: TextView, attendantNumber: Int) {
    textView.text = "$attendantNumber 人想去"
}

@BindingAdapter("getEventDate")
fun getEventDate(textView: TextView, eventDate: Long) {

    textView.text = eventDate.toFormattedTime()
}

@BindingAdapter("getCommentDate")
fun getCommentDate(textView: TextView, eventDate: Long) {

    textView.text = eventDate.toCommentFormattedTime()
}


////////// Artist Detail Page //////////
