package com.github.rozag.kueue.android.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.github.rozag.kueue.Kueue
import com.github.rozag.kueue.android.MainThreadExecutor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val queue = Kueue(
            workerExecutor = Executors.newSingleThreadExecutor(),
            callbackExecutor = MainThreadExecutor()
    )
    private lateinit var progressBar: ProgressBar
    private lateinit var generateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        generateBtn = findViewById(R.id.generate_btn)

        generateBtn.setOnClickListener { startRandomNumberGeneration() }
    }

    override fun onStart() {
        super.onStart()
        toInputState()
    }

    private fun startRandomNumberGeneration() {
        toGenerationState()
        queue.fromCallable { Thread.sleep(3000); (Math.random() * 100).toInt() }
                .onComplete { generatedNumber ->
                    toInputState()
                    showGeneratedNumber(generatedNumber)
                }
                .onError { throwable -> throwable.printStackTrace() }
                .go()
    }

    private fun toGenerationState() {
        progressBar.visibility = VISIBLE
        generateBtn.visibility = GONE
    }

    private fun toInputState() {
        progressBar.visibility = GONE
        generateBtn.visibility = VISIBLE
    }

    private fun showGeneratedNumber(number: Int) {
        Toast.makeText(this, "Random number: $number", Toast.LENGTH_SHORT).show()
    }

}