package com.dung.onlinemarketnodejs.base

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.androidadvance.topsnackbar.TSnackbar
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.net.URISyntaxException

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        // create mSocket

    }

    fun showMessage(message: String, view: View){
        var snack = TSnackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackbarView = snack.getView()
        val textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snack.show()
    }

}