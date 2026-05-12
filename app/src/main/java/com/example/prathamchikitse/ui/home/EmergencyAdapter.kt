package com.example.prathamchikitse.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prathamchikitse.R
import com.example.prathamchikitse.model.Emergency

class EmergencyAdapter(
    private var list: List<Emergency>,
    private val isKannada: Boolean,
    private val onClick: (Emergency) -> Unit
) : RecyclerView.Adapter<EmergencyAdapter.ViewHolder>() {

    private var filteredList: List<Emergency> = list

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvIcon: TextView = itemView.findViewById(R.id.tvIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emergency, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]

        holder.tvTitle.text = if (isKannada) item.name_kn else item.name_en
        holder.tvIcon.text = getEmergencyEmoji(item.name_en)

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            list
        } else {
            list.filter {
                it.name_en.contains(query, ignoreCase = true) ||
                        it.name_kn.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    private fun getEmergencyEmoji(name: String): String {
        return when (name.lowercase()) {
            "snake bite" -> "🐍"
            "heart attack" -> "❤️"
            "burns" -> "🔥"
            "choking adult", "choking infant" -> "😮‍💨"
            "bone fracture" -> "🦴"
            "severe bleeding" -> "🩸"
            "stroke" -> "🧠"
            "seizure" -> "🌀"
            "heat stroke" -> "☀️"
            "animal dog bite" -> "🐕"
            "electric shock" -> "⚡"
            "poisoning" -> "🧪"
            "near drowning" -> "🏊"
            "eye injury" -> "👁️"
            "head injury" -> "🤕"
            "diabetic emergency" -> "🍬"
            "asthma attack" -> "🫁"
            "allergic reaction" -> "🤧"
            "sprain" -> "🦶"
            else -> "🚑"
        }
    }
}