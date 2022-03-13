package br.com.tsmweb.inventapp.features.locale

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.databinding.ItemLocaleBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding

class LocaleAdapter(
    private val onClick: (LocaleBinding) -> Unit,
    private val onMenuItemClick: (MenuItem, LocaleBinding) -> Boolean
) : RecyclerView.Adapter<LocaleAdapter.ViewHolder>() {

    private var locales: List<LocaleBinding>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<LocaleBinding>?) {
        locales = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_locale, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = locales?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.apply {
            locales?.get(position)?.let { currentLocale ->
                locale = currentLocale
                executePendingBindings()

                clLocale.setOnClickListener {
                    onClick(currentLocale)
                }

                imgMenu.setOnClickListener {
                    val popup = PopupMenu(it.context, it)
                    popup.inflate(R.menu.item_locale_menu)
                    popup.setOnMenuItemClickListener { menuItem ->
                        onMenuItemClick(menuItem, currentLocale)
                    }
                    popup.show()
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemLocaleBinding>(view)
    }
}