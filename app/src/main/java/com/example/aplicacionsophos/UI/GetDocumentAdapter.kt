package com.example.aplicacionsophos.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.data.model.Document
import kotlinx.android.synthetic.main.document_list_item.view.*


class GetDocumentAdapter(val documentClick: (Int) -> Unit): RecyclerView.Adapter<GetDocumentAdapter.SearchViewHolder>() {
    var documentList: List<Document> = emptyList<Document>()

    fun setData(list: List<Document>){
        documentList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.document_list_item, parent,false))
    }

    override fun getItemCount(): Int {
        return documentList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val documento = documentList[position]
        holder.itemView.DateText.text = "${documento.Fecha} - ${documento.TipoAdjunto}"
        holder.itemView.NameText.text = "${documento.Nombre} - ${documento.Apellido}"
        holder.itemView.setOnClickListener { documentClick(position) }
    }

    class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}