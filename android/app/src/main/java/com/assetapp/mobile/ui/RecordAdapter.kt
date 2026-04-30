package com.assetapp.mobile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assetapp.mobile.data.AssetRecord
import com.assetapp.mobile.databinding.ItemRecordBinding
import java.text.NumberFormat
import java.util.Locale

class RecordAdapter(
    private val data: MutableList<AssetRecord>,
    private val onDelete: (AssetRecord) -> Unit
) : RecyclerView.Adapter<RecordAdapter.VH>() {

    private val fmt = NumberFormat.getCurrencyInstance(Locale.CHINA)

    class VH(val binding: ItemRecordBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = data[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvMeta.text = "${item.type} · ${item.category.ifBlank { "未分类" }}"
        holder.binding.tvAmount.text = fmt.format(item.amount)
        holder.binding.btnDelete.setOnClickListener { onDelete(item) }
    }
}
