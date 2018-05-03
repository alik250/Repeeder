package com.alce.repeeder

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textToBeRepeated = findViewById<TextView>(R.id.textToBeRepeated)
        val numberOfTimesToRepeat = findViewById<TextView>(R.id.numberTimesToRepeat)
        val resultsView = findViewById<TextView>(R.id.resultsView)

        val repeatButton = findViewById<Button>(R.id.repeatTextButton)
        val copyToClipboardButton = findViewById<Button>(R.id.copyToClipboardButton)

        var previousText: CharSequence = ""
        var previousNum: CharSequence = ""

        repeatButton.setOnClickListener {

            if (textToBeRepeated.text.isEmpty())
            {
                showToast(getString(R.string.Toast_Error_NoText))
            }
            else if (numberOfTimesToRepeat.text.isEmpty() || numberOfTimesToRepeat.text.toString().toInt() <= 0)
            {
                showToast(getString(R.string.Toast_Error_WrongNumber))
            }
            else if ((textToBeRepeated.text == previousText) && (numberOfTimesToRepeat.text == previousNum))
            {
                println("Debug: Nothing happened.")
                showToast("Nothing happened")
            }
            else
            {
                try
                {
                    resultsView.text = repeatText(
                            textToBeRepeated.text.toString(),
                            numberOfTimesToRepeat.text.toString().toInt()
                    )
                } catch (e: Exception)
                {
                    println("Exception trying to create string. Perhaps the string is too long? ")
                    println(e)
                    showToast(getString(R.string.Toast_Error_ResultError), false)
                }
            }
            println(previousText)
            println(previousNum)
            previousText = textToBeRepeated.text
            previousNum = numberOfTimesToRepeat.text

        }


        copyToClipboardButton.setOnClickListener {

            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                    "simple text",
                    resultsView.text
            )

            for (i in 1..1) {
                if (resultsView.text.isEmpty()) {
                    showToast(getString(R.string.Toast_Warning_NothingToCopy))
                    continue                                                        //Don't want to overwrite the clipboard with an empty clip
                }

                clipboard.primaryClip = clip

                if (clipboard.primaryClip.getItemAt(0).text.toString() == resultsView.text.toString()) {
                    showToast(getString(R.string.Toast_Success_CopiedToClipboard), false)
                } else {
                    showToast(getString(R.string.Toast_Error_FailedCopyingToClipboard))
                    println("on clipboard: " + clipboard.primaryClip.getItemAt(0).text)
                    println("resultsView:  " + resultsView.text)
                }
            }
        }
    }

    // Custom functions here

    private fun repeatText(string: String, numberOfTimes: Int): String {        //Primary logic
        var output = string
        for (i in 1 until numberOfTimes) {
            output += string
        }
        return output
    }


    private fun showToast(string: String, trueIfShort: Boolean = true){         //Generates Toasts on the fly
        Toast.makeText(this, string, if (trueIfShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
    }
}
