package com.arts.institutoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private var eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventTitle.text = event.title
        holder.eventDescription.text = event.description
        holder.eventDate.text = event.date
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun updateEventList(newEventList: List<Event>) {
        eventList = newEventList
        notifyDataSetChanged()
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitle: TextView = view.findViewById(R.id.eventTitle)
        val eventDescription: TextView = view.findViewById(R.id.eventDescription)
        val eventDate: TextView = view.findViewById(R.id.eventDate)
    }
}
