package com.assetapp.mobile

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.assetapp.mobile.data.AssetRecord
import com.assetapp.mobile.data.RecordStore
import com.assetapp.mobile.databinding.ActivityMainBinding
import com.assetapp.mobile.ui.RecordAdapter
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var store: RecordStore
    private val records = mutableListOf<AssetRecord>()
    private lateinit var adapter: RecordAdapter
    private val fmt = NumberFormat.getCurrencyInstance(Locale.CHINA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        store = RecordStore(this)
        records.addAll(store.load())

        binding.spType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("asset", "debt")
        )

        adapter = RecordAdapter(records) { record ->
            records.remove(record)
            store.save(records)
            adapter.notifyDataSetChanged()
            updateSummary()
        }
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
        binding.rvRecords.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val amount = binding.etAmount.text.toString().toDoubleOrNull()
            val type = binding.spType.selectedItem.toString()
            val category = binding.etCategory.text.toString().trim()
            if (name.isBlank() || amount == null || amount <= 0) return@setOnClickListener
            records.add(
                0,
                AssetRecord(
                    id = System.currentTimeMillis(),
                    name = name,
                    amount = amount,
                    type = type,
                    category = category
                )
            )
            store.save(records)
            binding.etName.setText("")
            binding.etAmount.setText("")
            binding.etCategory.setText("")
            adapter.notifyDataSetChanged()
            updateSummary()
        }

        updateSummary()
    }

    private fun updateSummary() {
        val asset = records.filter { it.type == "asset" }.sumOf { it.amount }
        val debt = records.filter { it.type == "debt" }.sumOf { it.amount }
        val net = asset - debt
        binding.tvSummary.text = "资产 ${fmt.format(asset)}  负债 ${fmt.format(debt)}  净资产 ${fmt.format(net)}"
    }
}
