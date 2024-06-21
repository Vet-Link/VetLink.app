package com.capstoneproject.vetlink.data.network

import com.capstoneproject.vetlink.data.model.LoginResponse
import com.capstoneproject.vetlink.data.model.PetProfileResponse
import com.capstoneproject.vetlink.data.model.PetResponse
import com.capstoneproject.vetlink.data.model.ProfileResponse
import com.capstoneproject.vetlink.data.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("/regisUser")
    fun signup(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("passwordVerify") passwordVerify: String
    ): Call<SignupResponse>

    @FormUrlEncoded
    @POST("/loginUser")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/userProfile")
    suspend fun profile(
        @Field("email") email: String
    ): ProfileResponse

    @FormUrlEncoded
    @POST("/addPet")
    fun addPet(
        @Field("pet_name") petName: String,
        @Field("pet_gender") petGender: String,
        @Field("pet_age") petAge: String,
        @Field("pet_species") petSpecies: String,
        @Field("pet_weight") petWeight: String
    ): Call<PetResponse>

    @GET("/showPet")
    suspend fun getPetProfile(
        @Query("ID") id: String
    ): PetProfileResponse
}
