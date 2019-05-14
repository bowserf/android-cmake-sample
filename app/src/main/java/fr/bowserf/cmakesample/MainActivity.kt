package fr.bowserf.cmakesample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculator = Calculator()

        val tvResultComputation = findViewById<TextView>(R.id.tv_result_computation)
        val editTextVal1 = findViewById<EditText>(R.id.edit_value1)
        val editTextVal2 = findViewById<EditText>(R.id.edit_value2)

        findViewById<Button>(R.id.btn_multiply).setOnClickListener {
            val value1 = editTextVal1.text.toString().toLong()
            val value2 = editTextVal2.text.toString().toLong()
            val result = calculator.multiply(value1, value2)
            tvResultComputation.text = resources.getString(R.string.multiplication_result, result)
        }

        findViewById<Button>(R.id.btn_addition).setOnClickListener {
            val value1 = editTextVal1.text.toString().toLong()
            val value2 = editTextVal2.text.toString().toLong()
            val result = calculator.add(value1, value2)
            tvResultComputation.text = resources.getString(R.string.addition_result, result)
        }

        findViewById<Button>(R.id.btn_subtraction).setOnClickListener {
            val value1 = editTextVal1.text.toString().toLong()
            val value2 = editTextVal2.text.toString().toLong()
            val result = calculator.minus(value1, value2)
            tvResultComputation.text = resources.getString(R.string.subtraction_result, result)
        }
    }

}
