package br.com.tsmweb.inventapp.features.place

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.databinding.ItemPlaceBinding
import br.com.tsmweb.inventapp.features.place.binding.PlaceBinding

class PlaceAdapter(
    private val onClick: (PlaceBinding) -> Unit,
    private val onMenuItemClick: (MenuItem, PlaceBinding) -> Boolean
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var places: List<PlaceBinding>? = null

    fun setData(data: List<PlaceBinding>?) {
        places = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = places?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.apply {
            places?.get(position)?.let { currentPlace ->
                place = currentPlace
                executePendingBindings()

                clPlace.setOnClickListener {
                    onClick(currentPlace)
                }

                imgMenu.setOnClickListener {
                    val popup = PopupMenu(it.context, it)
                    popup.inflate(R.menu.item_place_menu)
                    popup.setOnMenuItemClickListener { menuItem ->
                        onMenuItemClick(menuItem, currentPlace)
                    }
                    popup.show()
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemPlaceBinding>(view)
    }
}