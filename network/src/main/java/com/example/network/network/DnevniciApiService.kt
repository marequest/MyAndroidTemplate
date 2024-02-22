package com.example.network.network

import retrofit2.http.GET

interface DnevniciApiService {

    @GET("dnevnici")
    suspend fun getMyDnevnici(): String

    // U ViewModelu
//    fun getMarsPhotos() {
//        viewModelScope.launch {
//            marsUiState = MarsUiState.Loading
//            marsUiState = try {
//                MarsUiState.Success(marsPhotosRepository.getMarsPhotos())
//            } catch (e: IOException) {
//                MarsUiState.Error
//            } catch (e: HttpException) {
//                MarsUiState.Error
//            }
//        }
//    }

}