package br.com.tsmweb.inventapp.features.inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.databinding.ItemInventoryItemBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding

class InventoryItemAdapter(
    private val onClick: (InventoryItemBinding) -> Unit,
    private val onLongClick: (InventoryItemBinding) -> Boolean
) : RecyclerView.Adapter<InventoryItemAdapter.ViewHolder>() {

    private var inventoryItemBindings: List<InventoryItemBinding>? = null

    fun setData(data: List<InventoryItemBinding>?) {
        inventoryItemBindings = data
        notifyDataSetChanged()
    }

    fun getInventoryItem(position: Int) = inventoryItemBindings?.get(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = inventoryItemBindings?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.apply {
            inventoryItemBindings?.get(position)?.let { currentItem ->
                inventoryItem = currentItem
                executePendingBindings()

                clInventoryItem.setOnClickListener {
                    onClick(currentItem)
                }

                clInventoryItem.setOnLongClickListener {
                    onLongClick(currentItem)
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemInventoryItemBinding>(view)
    }
}