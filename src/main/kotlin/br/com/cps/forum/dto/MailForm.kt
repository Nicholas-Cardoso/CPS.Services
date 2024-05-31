package br.com.cps.forum.dto

import com.google.gson.annotations.SerializedName

data class MailForm(
    @SerializedName("recipientMail")
    val email: String,
    val subject: String,
    val body: String
)
