package com.codecollapse.tmdbmovies.common

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle


object CommonFunctions {


    private var progressDialog: ProgressDialog? = null

    inline fun <reified T : Activity> Activity.lunchActivity(
            options: Bundle? = null,
            noinline init: Intent.() -> Unit = {}
    ) {
        val intent = Intent(this, T::class.java)
        intent.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent, options)
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    inline fun <reified T : Any> Context.launchActivity(
            options: Bundle? = null,
            noinline init: Intent.() -> Unit = {}) {

        val intent = newIntent<T>(this)
        intent.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options)
        } else {
            startActivity(intent)
        }
    }

    inline fun <reified T : Any> newIntent(context: Context): Intent =
            Intent(context, T::class.java)


    fun showProgressDialog(mContext: Context, message: String?) {
        progressDialog = ProgressDialog(mContext)
        progressDialog!!.setMessage(message)
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    fun hideProgressDialog() {
        progressDialog!!.dismiss()
        progressDialog = null
    }
}