package fr.bowserf.cmakesample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculator = Calculator()

        val tvResultMultiplication = findViewById<TextView>(R.id.tv_result_multiply)

        findViewById<Button>(R.id.btn_multiply).setOnClickListener {
            val value1 = findViewById<EditText>(R.id.edit_value1).text.toString().toLong()
            val value2 = findViewById<EditText>(R.id.edit_value2).text.toString().toLong()
            val result = calculator.multiply(value1, value2)
            tvResultMultiplication.text = resources.getString(R.string.multiplication_result, result)
        }
    }

}
