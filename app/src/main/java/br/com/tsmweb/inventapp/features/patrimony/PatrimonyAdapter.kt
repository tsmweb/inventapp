package br.com.tsmweb.inventapp.features.patrimony

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.databinding.ItemPatrimonyBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding

class PatrimonyAdapter(
    private val onClick: (PatrimonyBinding) -> Unit,
    private val onLongClick: (PatrimonyBinding) -> Boolean
): RecyclerView.Adapter<PatrimonyAdapter.ViewHolder>() {

    private var patrimonies: List<PatrimonyBinding>? = null

    fun setData(data: List<PatrimonyBinding>?) {
        patrimonies = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patrimony, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount() = patrimonies?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.apply {
            patrimonies?.get(position)?.let { currentPatrimony ->
                patrimony = currentPatrimony
                executePendingBindings()

                clPatrimony.setOnClickListener {
                    onClick(currentPatrimony)
                }

                clPatrimony.setOnLongClickListener {
                    onLongClick(currentPatrimony)
                }
            }
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemPatrimonyBinding>(view)
    }
}