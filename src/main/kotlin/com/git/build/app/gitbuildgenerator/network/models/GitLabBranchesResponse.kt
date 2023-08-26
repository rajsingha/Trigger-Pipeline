package com.git.build.app.gitbuildgenerator.network.models

import com.google.gson.annotations.SerializedName


class GitLabBranchesResponse : ArrayList<GitLabBranchesResponseItem>()

data class GitLabBranchesResponseItem(
    @SerializedName("can_push")
    var canPush: Boolean? = null,
    @SerializedName("commit")
    var commit: Commit? = null,
    @SerializedName("default")
    var default: Boolean? = null,
    @SerializedName("developers_can_merge")
    var developersCanMerge: Boolean? = null,
    @SerializedName("developers_can_push")
    var developersCanPush: Boolean? = null,
    @SerializedName("merged")
    var merged: Boolean? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("protected")
    var `protected`: Boolean? = null,
    @SerializedName("web_url")
    var webUrl: String? = null
){
    data class Commit(
        @SerializedName("author_email")
        var authorEmail: String? = null,
        @SerializedName("author_name")
        var authorName: String? = null,
        @SerializedName("authored_date")
        var authoredDate: String? = null,
        @SerializedName("committed_date")
        var committedDate: String? = null,
        @SerializedName("committer_email")
        var committerEmail: String? = null,
        @SerializedName("committer_name")
        var committerName: String? = null,
        @SerializedName("created_at")
        var createdAt: String? = null,
        @SerializedName("id")
        var id: String? = null,
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("parent_ids")
        var parentIds: Any? = null,
        @SerializedName("short_id")
        var shortId: String? = null,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("trailers")
        var trailers: Any? = null,
        @SerializedName("web_url")
        var webUrl: String? = null
    )
}