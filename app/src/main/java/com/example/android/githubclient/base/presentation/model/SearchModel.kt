package com.example.android.githubclient.base.presentation.model

import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 09.03.2018.
 */
class SearchModel<T> {
    @SerializedName("total_count")
    var totalCount: Int? = null
    @SerializedName("incomplete_results")
    var incompleteResult: Boolean? = null
    @SerializedName("items")
    var data: List<T>? = null
}