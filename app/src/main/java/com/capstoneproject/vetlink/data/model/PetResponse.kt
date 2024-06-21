package com.capstoneproject.vetlink.data.model

import com.google.gson.annotations.SerializedName

data class PetResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
