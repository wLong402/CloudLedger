package com.assetapp.mobile.data

data class AssetRecord(
    val id: Long,
    val name: String,
    val amount: Double,
    val type: String,
    val category: String
)
