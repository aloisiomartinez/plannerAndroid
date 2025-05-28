package com.example.planner.data.model

data class Profile (
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String = ""// Imagem no formato base64
) {
    fun isValid(): Boolean =
        name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && image.isNotEmpty()

}
