package com.iwdael.fetcher.example

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.iwdael.fetcher.*

class MainActivity : AppCompatActivity() {
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        textView = findViewById(R.id.tvcontent)
        Silky.source("Android").prepared("Pre").into(textView)
    }

    private fun init() {
        Silky.init(Config.defaultConfig)
        Silky.chainFetcher(
            Chain.newFetchBuilder()
                .fetch(String::class.java) { Fetcher { s: String -> s.toShort() } }
                .fetch { Fetcher { obj: Short -> obj.toInt() } }
                .build(Int::class.java)
        )
        Silky.chainInjector(
            Chain.newInjectBuilder()
                .transform(Int::class.java) { Transformer { obj: Int -> java.lang.String.valueOf(obj) } }
                .inject { Injector<String, TextView> { target, source -> target.text = source } }
                .build(TextView::class.java)
        )
        Silky.chainInjector(
            Chain.newInjectBuilder()
                .transform(String::class.java) { Transformer { obj: String -> obj } }
                .inject { Injector<String, TextView> { target, source -> target.setText(source) } }
                .build(TextView::class.java)
        )
    }
}