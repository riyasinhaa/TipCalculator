package `in`.riyasinha.tipnbillsplitcalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseValue: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipValue: TextView
    private lateinit var totalValue: TextView
    private lateinit var seekBarPerson: SeekBar
    private lateinit var tvPPValue: TextView
    private lateinit var tvTipDescription: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTipValue = findViewById(R.id.tvTipValue)
        etBaseValue = findViewById(R.id.etBaseValue)
        seekBarTip = findViewById(R.id.seekBarTip)
        totalValue = findViewById(R.id.totalValue)
        seekBarPerson = findViewById(R.id.seekBarPerson)
        tvPPValue = findViewById(R.id.tvPPValue)
        tvTipDescription = findViewById(R.id.tvTipDescription)

        val tvTipPercent: TextView = findViewById(R.id.tvTipPercent)
        val tvNoOfPerson: TextView = findViewById(R.id.tvNoOfPerson)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercent.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                tvTipPercent.text = "$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        etBaseValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                computeTipAndTotal()
            }
        })

        seekBarPerson.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                tvNoOfPerson.text = "$progress"
                computeTipAndTotal()

            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription: String
        when(tipPercent){
            in 0..9 -> tipDescription = "Poor"
            in 10..14 -> tipDescription = "Acceptable"
            in 15..19 -> tipDescription = "Good"
            in 20..24 -> tipDescription = "Great"
            else -> tipDescription = "Amazing"
        }
        tvTipDescription.text = tipDescription
    }


    private fun computeTipAndTotal() {
        if(etBaseValue.text.toString().isEmpty()){
            tvTipValue.text = ""
            totalValue.text = ""
            tvPPValue.text = " "
        }
        else{
        val baseAmount = etBaseValue.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val tipAmount = baseAmount*tipPercent / 100
        tvTipValue.text = "$tipAmount"
        val totalAmount = baseAmount + tipAmount
        totalValue.text = "$totalAmount"
        val noOfPerson = seekBarPerson.progress
        val perPersonValue = totalAmount / noOfPerson
        val PPValue = String.format("%.2f", perPersonValue)
            tvPPValue.text =  PPValue

            }
        }
    }