package com.git.build.app.gitbuildgenerator.network.models


import com.google.gson.annotations.SerializedName

data class PipeLineResponse(
    @SerializedName("before_sha")
    var beforeSha: String? = null,
    @SerializedName("committed_at")
    var committedAt: Any? = null,
    @SerializedName("coverage")
    var coverage: Any? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("detailed_status")
    var detailedStatus: DetailedStatus? = null,
    @SerializedName("duration")
    var duration: Any? = null,
    @SerializedName("finished_at")
    var finishedAt: Any? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("iid")
    var iid: Int? = null,
    @SerializedName("project_id")
    var projectId: Int? = null,
    @SerializedName("queued_duration")
    var queuedDuration: Any? = null,
    @SerializedName("ref")
    var ref: String? = null,
    @SerializedName("sha")
    var sha: String? = null,
    @SerializedName("source")
    var source: String? = null,
    @SerializedName("started_at")
    var startedAt: Any? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tag")
    var tag: Boolean? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("user")
    var user: User? = null,
    @SerializedName("web_url")
    var webUrl: String? = null,
    @SerializedName("yaml_errors")
    var yamlErrors: Any? = null
){
    data class DetailedStatus(
        @SerializedName("details_path")
        var detailsPath: String? = null,
        @SerializedName("favicon")
        var favicon: String? = null,
        @SerializedName("group")
        var group: String? = null,
        @SerializedName("has_details")
        var hasDetails: Boolean? = null,
        @SerializedName("icon")
        var icon: String? = null,
        @SerializedName("illustration")
        var illustration: Any? = null,
        @SerializedName("label")
        var label: String? = null,
        @SerializedName("text")
        var text: String? = null,
        @SerializedName("tooltip")
        var tooltip: String? = null
    )
    data class User(
        @SerializedName("avatar_url")
        var avatarUrl: String? = null,
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("state")
        var state: String? = null,
        @SerializedName("username")
        var username: String? = null,
        @SerializedName("web_url")
        var webUrl: String? = null
    )
}
