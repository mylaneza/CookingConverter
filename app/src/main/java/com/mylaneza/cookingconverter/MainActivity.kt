package com.mylaneza.cookingconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Metric System Unit Spinner */
        val metricSystemUnitList = listOf("cm","m","km")
        val adapter =  ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,metricSystemUnitList)
        val metricUnitsSpinner = findViewById<Spinner>(R.id.metric_system_unit)
        metricUnitsSpinner.adapter = adapter

        /* Imperial System Unit Spinner */

        val imperialSystemUnitList = listOf("mi","ft" , "in")
        val adapter2 = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,imperialSystemUnitList)
        val imperialUnitsSpinner = findViewById<Spinner>(R.id.imperial_system_unit)
        imperialUnitsSpinner.adapter = adapter2
    }
}