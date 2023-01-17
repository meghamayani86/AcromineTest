package com.app.acrominetest.repository

import com.app.acrominetest.models.AcromineResponse
import com.app.acrominetest.retrofit.AcromineAPI
import com.app.acrominetest.utils.NetworkResult
import javax.inject.Inject

class AcromineRepository @Inject constructor(private val acromineAPI: AcromineAPI) {
    suspend fun fetchAcromine(acronym: String?): NetworkResult<List<AcromineResponse>>? {

        val result = acromineAPI.fetchAcromine(acronym)
        return if (result.isSuccessful && result.body() != null) {

            result.body()?.let {
                if (it.isNotEmpty()) {
                    NetworkResult.Success(it)

                } else {
                    NetworkResult.Error("No data found. Search for Abbreviations!!")
                }
            }
        } else {
            NetworkResult.Error("Something Went Wrong!!")
        }
    }


}