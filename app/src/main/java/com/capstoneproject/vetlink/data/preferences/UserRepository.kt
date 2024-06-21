//package com.capstoneproject.vetlink.data.preferences
//
//import com.capstoneproject.vetlink.data.network.ApiService
//import com.capstoneproject.vetlink.data.model.LoginResponse
//import com.capstoneproject.vetlink.data.utils.Resource
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.withContext
//import retrofit2.HttpException
//
//class UserRepository(private val apiService: com.capstoneproject.vetlink.data.network.ApiService, val userPreferences: UserPreferences) {
//
//    suspend fun verifyEmail(userId: String, token: String): Resource<VerifyEmailResponse> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = apiService.verifyEmail("user", userId, token).execute()
//                if (response.isSuccessful) {
//                    val verifyEmailResponse = response.body()
//                    if (verifyEmailResponse != null) {
//                        Resource.Success(verifyEmailResponse)
//                    } else {
//                        Resource.Error("Empty response body")
//                    }
//                } else {
//                    val errorResponse = response.errorBody()?.string()
//                    Resource.Error("Failed to verify email: ${response.code()} - $errorResponse")
//                }
//            } catch (e: HttpException) {
//                val errorMessage = e.response()?.errorBody()?.string()
//                Resource.Error("HTTP Exception: ${e.code()} - $errorMessage")
//            } catch (e: Exception) {
//                Resource.Error("Exception: ${e.message ?: e.toString()}")
//            }
//        }
//    }
//
//    suspend fun login(email: String, password: String): Resource<LoginResponse> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = apiService.login(email, password).execute()
//                if (response.isSuccessful) {
//                    val loginResponse = response.body()
//                    if (loginResponse != null) {
//                        userPreferences.saveAuthToken(loginResponse.data ?: "")
//                        Resource.Success(loginResponse)
//                    } else {
//                        Resource.Error("Empty response body")
//                    }
//                } else {
//                    val errorResponse = response.errorBody()?.string()
//                    Resource.Error("Failed to login: ${response.code()} - $errorResponse")
//                }
//            } catch (e: HttpException) {
//                val errorMessage = e.response()?.errorBody()?.string()
//                Resource.Error("HTTP Exception: ${e.code()} - $errorMessage")
//            } catch (e: Exception) {
//                Resource.Error("Exception: ${e.message ?: e.toString()}")
//            }
//        }
//    }
//
//
//    suspend fun saveAuthToken(token: String) {
//        userPreferences.saveAuthToken(token)
//    }
//
//    suspend fun clearAuthToken() {
//        userPreferences.clearAuthToken()
//    }
//
//    suspend fun getAuthToken(): String? {
//        return userPreferences.authToken.first()
//    }
//}