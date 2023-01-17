package com.app.acrominetest.retrofit

import com.app.acrominetest.models.AcromineResponse
import com.app.acrominetest.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcromineAPI {

    @GET(Constants.ACRONYM_END_URL)
    suspend fun fetchAcromine(@Query("sf") string: String?): Response<List<AcromineResponse>>
}