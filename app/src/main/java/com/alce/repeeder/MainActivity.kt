package com.alce.repeeder

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val incorrectTextToast = Toast.makeText(this, "No text entered", Toast.LENGTH_SHORT)
        val incorrectNumToast = Toast.makeText(this, "Incompatible number value entered", Toast.LENGTH_SHORT)
        val resultErrorToast = Toast.makeText(this, "Error occurred creating output", Toast.LENGTH_LONG)
        val copiedToClipboardToast = Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT)
        val notCopiedToClipboardToast = Toast.makeText(this, "Error copying to clipboard. Try copying manually. ", Toast.LENGTH_LONG)

        val textToBeRepeated = findViewById<TextView>(R.id.textToBeRepeated)
        val numberOfTimesToRepeat = findViewById<TextView>(R.id.numberTimesToRepeat)
        val resultsView = findViewById<TextView>(R.id.resultsView)

        val repeatButton = findViewById<Button>(R.id.repeatTextButton)
        val copyToClipboardButton = findViewById<Button>(R.id.copyToClipboardButton)


        repeatButton.setOnClickListener {
            if (textToBeRepeated.text.isEmpty()) {
                incorrectTextToast.show()
            } else if (numberOfTimesToRepeat.text.isEmpty() || numberOfTimesToRepeat.text.toString().toInt() <= 0) {
                incorrectNumToast.show()
            } else {
                try {
                    resultsView.text = repeatText(
                            textToBeRepeated.text.toString(),
                            numberOfTimesToRepeat.text.toString().toInt()
                    )
                } catch (e: Exception) {
                    println("Exception trying to create string. Perhaps the string is too long? ")
                    println(e)
                    resultErrorToast.show()
                }
            }
        }


        copyToClipboardButton.setOnClickListener {

            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                    "simple text",
                    resultsView.text
            )

            clipboard.primaryClip = clip

            if (clipboard.primaryClip.getItemAt(0).text.toString() == resultsView.text.toString()) {
                copiedToClipboardToast.show()
            } else {
                notCopiedToClipboardToast.show()
                println("on clipboard: " + clipboard.primaryClip.getItemAt(0).text)
                println("resultsView:  " + resultsView.text)
            }
        }
    }


    fun repeatText(string: String, numberOfTimes: Int): String {
        var output = string
        for (i in 1..numberOfTimes - 1) {
            output += string
        }
        return output
    }


}
