package com.example.weatherapplication_xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication_xml.databinding.ItemBinding
import com.example.weatherapplication_xml.domain.weather.WeatherData
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class WeatherForecastAdapter() :
    RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    private val dataSet: ArrayList<WeatherData> = arrayListOf()


    fun emit(newList: List<WeatherData>?) {
        dataSet.clear()
        dataSet.addAll(newList ?: arrayListOf())
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: WeatherData) {

            val temperature = "${data.temperatureCelsius.toString()}°C"
            binding.temperature.text = temperature
            binding.time.text = data.time.format(
                DateTimeFormatter.ofPattern("HH:mm")
            )
            binding.weatherImage.setImageResource(data.weatherType.iconRes)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])

    }


    override fun getItemCount(): Int = dataSet.size

}

