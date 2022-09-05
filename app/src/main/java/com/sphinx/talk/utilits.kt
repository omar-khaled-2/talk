package com.sphinx.talk

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.navigation.NavHostController
import android.provider.MediaStore
import java.io.*


fun NavHostController.replace(route:String){
    this.navigate(route){
        this.popUpTo(this@replace.currentDestination!!.route!!){
            this.inclusive = true
        }
    }
}



fun ContentResolver.getName(uri: Uri): String? {
    val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
    val metaCursor = this.query(uri, projection, null, null, null)
    if (metaCursor !== null && metaCursor.moveToFirst()) return metaCursor.getString(0)
    return null
}



fun ContentResolver.getFile(contentUri:String, pathname:String,defaultName:String): File {
    val uri = Uri.parse(contentUri)
    val inputStream = this.openInputStream(uri!!)
    val file = File(pathname,this.getName(uri) ?: defaultName)
    val outPutStream = FileOutputStream(file)
    inputStream!!.copyTo(outPutStream)
    return file

}

fun ContentResolver.toByteArray(contentUri:String): ByteArray {
    val uri = Uri.parse(contentUri)
    val inputStream = this.openInputStream(uri!!)
    val byteArray = ByteArrayOutputStream()
    inputStream!!.copyTo(byteArray)
    return byteArray.toByteArray()
}




fun String.CapitilizeFirstLetter(): String {
    return this[0].uppercase() + this.substring(1)
}