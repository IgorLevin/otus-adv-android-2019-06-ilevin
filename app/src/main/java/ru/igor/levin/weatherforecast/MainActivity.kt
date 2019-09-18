package ru.igor.levin.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        tvText.text = id
    }
}
