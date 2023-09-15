package com.example.musicchaser

import android.widget.TextView
import androidx.databinding.BindingAdapter


////////// Profile Page //////////
@BindingAdapter("getUserNickname")
fun getUserNickname(textView: TextView, userNickname: String) {
    textView.text = "暱稱：$userNickname"
}

@BindingAdapter("getUserAccount")
fun getUserAccount(textView: TextView, userAccount: String) {
    textView.text = "使用者帳號：$userAccount"
}