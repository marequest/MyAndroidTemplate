package com.example.network.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    companion object {

        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://your-api-base-url.com/"

        fun getRetrofitInstance(): Retrofit {
            if (retrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    // TODO
//                    .addInterceptor(AuthInterceptor())
                    // Add other interceptors if needed
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

        private class AuthInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest: Request = chain.request()

                // Add your authentication logic here (e.g., add headers)
                val newRequest: Request = originalRequest.newBuilder()
                    .header("Authorization", "Bearer YOUR_ACCESS_TOKEN")
                    .build()

                return chain.proceed(newRequest)
            }
        }
    }

}