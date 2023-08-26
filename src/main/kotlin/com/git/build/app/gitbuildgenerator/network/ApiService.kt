package com.git.build.app.gitbuildgenerator.network

import com.git.build.app.gitbuildgenerator.network.models.GitLabBranchesResponse
import com.git.build.app.gitbuildgenerator.network.models.PipeLineResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("repository/branches")
    fun getBranches(
        @Header("PRIVATE-TOKEN") privateToken: String,
        @Query("per_page") items: Int,
        @Query("search") search: String? = null
    ): Call<GitLabBranchesResponse>

    @FormUrlEncoded
    @POST("trigger/pipeline")
    fun triggerPipeLine(@FieldMap params: Map<String, @JvmSuppressWildcards Any>): Call<PipeLineResponse>
}
