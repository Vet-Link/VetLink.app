package com.capstoneproject.vetlink.data.model

import com.google.gson.annotations.SerializedName

data class PetProfileResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val pets: List<PetProfile>?
)

data class PetProfile(
    @SerializedName("pet_name")
    val petName: String?,
    @SerializedName("pet_gender")
    val petGender: Boolean?,
    @SerializedName("pet_age")
    val petAge: Int?,
    @SerializedName("pet_species")
    val petSpecies: String?,
    @SerializedName("pet_weight")
    val petWeight: Int?
)