package com.agenda.arv1.util.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agenda.arv1.R
import com.agenda.arv1.data.model.PontoRecordacao

class CustomAdapter(
    private var itemDeleteCallback: ((item: PontoRecordacao) -> Unit)? = null,
    private var itemTouchedCallback: ((item: PontoRecordacao) -> Unit)? = null) : ListAdapter<PontoRecordacao, CustomAdapter.ViewHolder>(callback){

    companion object {
        private val callback = object : DiffUtil.ItemCallback<PontoRecordacao>() {
            override fun areItemsTheSame(oldItem: PontoRecordacao, newItem: PontoRecordacao): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: PontoRecordacao, newItem: PontoRecordacao): Boolean {
                return oldItem.uid == newItem.uid
                        && oldItem.nome == newItem.nome
                        && oldItem.descricao == newItem.descricao
                        && oldItem.lat == newItem.lat
                        && oldItem.lng == newItem.lng
            }
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titulo = view.findViewById<TextView>(R.id.tituloText)
        val desc = view.findViewById<TextView>(R.id.descText)
        val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memorias_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        val context = holder.itemView.context
        holder.titulo.text = item.nome
        holder.desc.text = item.descricao

        holder.itemView.setOnClickListener {
            itemTouchedCallback?.let { it(item) }
        }


        holder.btnDelete.setOnClickListener {
            itemDeleteCallback?.let { cb ->
                cb(item)
            }
        }

    }
}
