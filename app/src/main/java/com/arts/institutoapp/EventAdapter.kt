package com.arts.institutoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private var eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var selectedPosition: Int = -1 // Variable para almacenar la posición seleccionada

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventTitle.text = event.title
        holder.eventDescription.text = event.description
        holder.eventDate.text = event.date

        // Cambiar el fondo del elemento si está seleccionado
        holder.itemView.isSelected = position == selectedPosition
        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged() // Refrescar para reflejar la selección
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun updateEventList(newEventList: List<Event>) {
        eventList = newEventList
        notifyDataSetChanged()
    }

    // Método para obtener la posición seleccionada
    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitle: TextView = view.findViewById(R.id.eventTitle)
        val eventDescription: TextView = view.findViewById(R.id.eventDescription)
        val eventDate: TextView = view.findViewById(R.id.eventDate)
    }
}
