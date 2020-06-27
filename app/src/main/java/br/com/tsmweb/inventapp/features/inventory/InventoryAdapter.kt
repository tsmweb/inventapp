package br.com.tsmweb.inventapp.features.inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.databinding.ItemInventoryBinding
import br.com.tsmweb.presentation.inventory.binding.InventoryBinding

class InventoryAdapter(
    private val onClick: (InventoryBinding) -> Unit,
    private val onLongClick: (InventoryBinding) -> Boolean
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {

    private var inventoryBindings: List<InventoryBinding>? = null

    fun setData(data: List<InventoryBinding>?) {
        inventoryBindings = data
        notifyDataSetChanged()
    }

    fun sortData(sortDesc: Boolean) {
        if (sortDesc) {
            inventoryBindings?.sortedByDescending { inventoryBinding -> inventoryBinding.dateInventory }
        } else {
            inventoryBindings?.sortedBy { inventoryBinding -> inventoryBinding.dateInventory}
        }

        notifyDataSetChanged()
    }

    fun getInventory(position: Int) = inventoryBindings?.get(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = inventoryBindings?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.apply {
            inventoryBindings?.get(position)?.let { currentInventory ->
                inventory = currentInventory
                executePendingBindings()

                clInventory.setOnClickListener {
                    onClick(currentInventory)
                }

                clInventory.setOnLongClickListener {
                    onLongClick(currentInventory)
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemInventoryBinding>(view)
    }
}