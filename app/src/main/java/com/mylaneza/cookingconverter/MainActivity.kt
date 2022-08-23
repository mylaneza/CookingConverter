package com.mylaneza.cookingconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.mylaneza.cookingconverter.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    companion object{

        /* Metrical System Units */
        const val MILILITTER = "ml"
        const val GRAMS = "g"


        //TODO: SET REAL VALUES FOR CONVERTION FACTORS
        const val GRAMS_TO_SPOON_FACTOR = 1.0
        const val GRAMS_TO_CUP_FACTOR = 2.0
        const val GRAMS_TO_OUNCE_FACTOR = 3.0

        const val MILILITTER_TO_SPOON_FACTOR = 5.0
        const val MILILITTER_TO_CUP_FACTOR = 6.0
        const val MILILITTER_TO_OUNCE_FACTOR = 7.0

        /* Imperial System Units */
        const val SPOON = "tsp"
        const val CUP = "c"
        const val OUNCE = "oz"

    }

    private lateinit var binding : ActivityMainBinding


    private val metricSystemUnitList = listOf( MILILITTER ,GRAMS)
    private val imperialSystemUnitList = listOf(SPOON, CUP , OUNCE)

    private lateinit var upToDown : String
    private lateinit var downToUp : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upToDown = getString(R.string.up_to_down)
        downToUp = getString(R.string.down_to_up)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Metric System Unit Spinner */
        initSpinnerList(binding.metricSystemUnit,metricSystemUnitList)

        /* Imperial System Unit Spinner */
        initSpinnerList(binding.imperialSystemUnit,imperialSystemUnitList)

        binding.convertButton.setOnClickListener{
            convertUnits()
        }

        binding.sideConvertionButton.setOnClickListener { changeSideToConvert() }
    }

    /**
     * Changes the side to convert
     */
    private fun changeSideToConvert() {

        if(binding.sideConvertionButton.text.toString().equals(downToUp)) {
            binding.sideConvertionButton.text = upToDown

        }else {
            binding.sideConvertionButton.text = downToUp
        }
    }

    /**
     * Initialize the spinner list of elements
     */
    fun initSpinnerList( spinner : Spinner , list : List<String>){
        val adapter =  ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,list)
        spinner.adapter = adapter
    }

    /**
     * Makes the convertion of the cooking units
     */
    private fun convertUnits() {
        if(binding.sideConvertionButton.text.toString().equals(upToDown)){
            val metricValue = binding.metricalSystemTextEdit.text.toString().toDoubleOrNull()
            if( metricValue == null ){
                Snackbar.make(binding.root,"Metric Value missing.",Snackbar.LENGTH_SHORT).show()
                binding.imperialSystemTextEdit.setText("")
                return
            }

            val convertedFactor = when(binding.metricSystemUnit.selectedItem.toString()){
                MILILITTER -> {
                    when( binding.imperialSystemUnit.selectedItem.toString() ){
                        SPOON -> MILILITTER_TO_SPOON_FACTOR
                        CUP -> MILILITTER_TO_CUP_FACTOR
                        OUNCE -> MILILITTER_TO_OUNCE_FACTOR
                        else -> 0.0
                    }
                }
                GRAMS -> {
                    when( binding.imperialSystemUnit.selectedItem.toString() ){
                        SPOON -> GRAMS_TO_SPOON_FACTOR
                        CUP -> GRAMS_TO_CUP_FACTOR
                        OUNCE -> GRAMS_TO_OUNCE_FACTOR
                        else -> 0.0
                    }
                }
                else -> 0.0
            }

            binding.imperialSystemTextEdit.setText( getString(R.string.value,NumberFormat.getInstance().format(metricValue * convertedFactor)))
        }
        else {
            val imperialValue = binding.imperialSystemTextEdit.text.toString().toDoubleOrNull()
            if( imperialValue == null){
                Snackbar.make(binding.root,"Imperial Value missing.",Snackbar.LENGTH_SHORT).show()
                binding.metricalSystemTextEdit.setText("")
                return
            }

            val convertedFactor = when(binding.imperialSystemUnit.selectedItem.toString()){
                SPOON -> {
                    when( binding.metricSystemUnit.selectedItem.toString() ){
                        MILILITTER -> 1/MILILITTER_TO_SPOON_FACTOR
                        GRAMS -> 1/ GRAMS_TO_SPOON_FACTOR
                        else -> 0.0
                    }
                }
                CUP -> {
                    when( binding.metricSystemUnit.selectedItem.toString() ){
                        MILILITTER -> 1/MILILITTER_TO_CUP_FACTOR
                        GRAMS -> 1/GRAMS_TO_CUP_FACTOR
                        else -> 0.0
                    }
                }
                OUNCE -> {
                    when( binding.metricSystemUnit.selectedItem.toString() ){
                        MILILITTER -> 1/MILILITTER_TO_OUNCE_FACTOR
                        GRAMS -> 1/GRAMS_TO_OUNCE_FACTOR
                        else -> 0.0
                    }
                }
                else -> 0.0
            }
            binding.metricalSystemTextEdit.setText( getString(R.string.value,NumberFormat.getInstance().format(imperialValue * convertedFactor)))
        }
    }




}