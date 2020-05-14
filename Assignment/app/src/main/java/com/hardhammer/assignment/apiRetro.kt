package com.hardhammer.assignment

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.Call

import retrofit2.http.GET

interface  apiRetro{
    @GET("repositories/19438/issues")
    fun getGitResponse():Observable<List<apiResponse>>
}

data class  apiResponse(
    @SerializedName("repository_url")
    val repositoryurl: String? =null,
    @SerializedName("user")
    val user:User?=null,
    @SerializedName("state")
    val state:String?=null
)

data class User(
    @SerializedName("login")
    val login:String?=null,
    @SerializedName("id")
    val id:Int?=null
)