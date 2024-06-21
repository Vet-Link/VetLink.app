package com.capstoneproject.vetlink.data.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("profileUrl")
	val profileUrl: String? = null,

	@field:SerializedName("userEmail")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
