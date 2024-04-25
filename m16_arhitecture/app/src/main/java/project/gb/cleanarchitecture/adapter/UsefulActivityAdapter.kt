package project.gb.cleanarchitecture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.gb.cleanarchitecture.R
import project.gb.cleanarchitecture.data.UsefulActivityDto

class UsefulActivityAdapter : RecyclerView.Adapter<UsefulActivityAdapter.ViewHolder>() {
    private var data: List<UsefulActivityDto> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.field_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_for_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        val activity = item.activity.ifEmpty { "N/A" }
        val type = item.type.ifEmpty { "N/A" }
        val participants = if (item.participants != 0) item.participants.toString() else "N/A"
        val price = if (item.price != 0.0) item.price.toString() else "N/A"
        val link = item.link.ifEmpty { "N/A" }
        val key = item.key.ifEmpty { "N/A" }
        val accessibility = if (item.accessibility != 0.0) item.accessibility.toString() else "N/A"

        val activityInfo = "Activity: $activity\n" +
                "Type: $type\n" +
                "Participants: $participants\n" +
                "Price: $price\n" +
                "Link: $link\n" +
                "Key: $key\n" +
                "Accessibility: $accessibility"

        holder.textView.text = activityInfo
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<UsefulActivityDto>) {
        data = newData
        notifyDataSetChanged()
    }
}