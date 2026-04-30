package com.assetapp.mobile.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class RecordStore(context: Context) {
    private val sp = context.getSharedPreferences("asset_records", Context.MODE_PRIVATE)

    fun load(): MutableList<AssetRecord> {
        val raw = sp.getString("records", "[]") ?: "[]"
        val arr = JSONArray(raw)
        val result = mutableListOf<AssetRecord>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            result.add(
                AssetRecord(
                    id = o.optLong("id"),
                    name = o.optString("name"),
                    amount = o.optDouble("amount"),
                    type = o.optString("type"),
                    category = o.optString("category")
                )
            )
        }
        return result
    }

    fun save(list: List<AssetRecord>) {
        val arr = JSONArray()
        list.forEach {
            arr.put(
                JSONObject().apply {
                    put("id", it.id)
                    put("name", it.name)
                    put("amount", it.amount)
                    put("type", it.type)
                    put("category", it.category)
                }
            )
        }
        sp.edit().putString("records", arr.toString()).apply()
    }
}
