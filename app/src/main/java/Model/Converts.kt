package Model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter

import java.io.ByteArrayOutputStream

class Converts {

    // convert bitmap to ByteArray, store ByteArray into DB
    @TypeConverter
    fun fromBitmapToByte(bitmap: Bitmap):ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }

    // ByteArray convert to Bitmap
    @TypeConverter
    fun fromByteToBitmap(byteArray: ByteArray):Bitmap{
       return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}