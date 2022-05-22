package com.example.itoken.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.itoken.databinding.ViewNotificationBinding

object CommonUtils {

    fun makeToast(context: Context?, message: String?) =
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        } ?: Unit

    fun showDialog(context: Context?, text: String?, onClickEvent: (() -> Unit)) {
        val bindingOfDialog: ViewNotificationBinding
        val alerts = AlertDialog.Builder(context).apply {
            bindingOfDialog = ViewNotificationBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        with(bindingOfDialog) {
            btnNo.setOnClickListener {
                alerts.dismiss()
            }
            btnYes.setOnClickListener {
                onClickEvent.invoke()
                alerts.dismiss()
            }
            tvNotification.text = text
        }
    }
}