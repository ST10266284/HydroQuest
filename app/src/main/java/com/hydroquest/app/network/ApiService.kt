package com.hydroquest.app.network

import com.hydroquest.app.model.Gamification
import com.hydroquest.app.model.LogIntakeRequest
import com.hydroquest.app.model.UpdateSettingsRequest
import com.hydroquest.app.model.User
import com.hydroquest.app.model.UserSettingsRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response

interface ApiService {

    @POST("api/user/auth")
    suspend fun auth(@Body idToken: String): Response<User>

    //@PUT("api/user/settings/{userId}")
    //suspend fun updateSettings(@Path("userId") userId: String, @Body settings: Map<String, Any>): Response<Unit>
    //@PUT("api/user/settings/{userId}")
    //suspend fun updateSettings(@Path("userId") userId: String, @Body settings: UserSettingsRequest): Response<Unit>
    @PUT("api/user/settings/{userId}")
    suspend fun updateSettings(@Path("userId") userId: String, @Body settings: UpdateSettingsRequest): Response<Unit>

    @POST("api/hydration/log")
    suspend fun logIntake(@Body log: LogIntakeRequest): Response<Unit>


    @GET("api/hydration/daily/{userId}")
    suspend fun getDailyIntake(@Path("userId") userId: String): Response<Int>

    @GET("api/hydration/weekly/{userId}")
    suspend fun getWeeklyIntake(@Path("userId") userId: String): Response<List<Int>>


    @POST("api/gamification/update/{userId}/{amount}")
    suspend fun updateGamification(@Path("userId") userId: String, @Path("amount") amount: Int): Response<Unit>

    @GET("api/gamification/{userId}")
    suspend fun getGamification(@Path("userId") userId: String): Response<Gamification>
}