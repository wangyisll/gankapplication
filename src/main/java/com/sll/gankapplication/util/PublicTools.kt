/**
 * Copyright 2017 yidong
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package onlyloveyd.com.gankioclient.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.sll.gankapplication.constant.Constant.ONE_DAY
import com.sll.gankapplication.constant.Constant.ONE_HOUR
import com.sll.gankapplication.constant.Constant.ONE_MINUTE
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


object PublicTools {

    /**
     * 获取目标时间和当前时间之间的差距

     * @param date date
     * *
     * @return String
     */
    fun getTimestampString(date: Date): String {
        val curDate = Date()
        val splitTime = curDate.time - date.time
        if (splitTime < 30 * ONE_DAY) {
            if (splitTime < ONE_MINUTE) {
                return "刚刚"
            }
            if (splitTime < ONE_HOUR) {
                return String.format("%d分钟前", splitTime / ONE_MINUTE)
            }

            if (splitTime < ONE_DAY) {
                return String.format("%d小时前", splitTime / ONE_HOUR)
            }

            return String.format("%d天前", splitTime / ONE_DAY)
        }
        val result: String
        result = "M月d日 HH:mm"
        return SimpleDateFormat(result, Locale.CHINA).format(date)
    }

    /**
     * Date（long） 转换 String

     * @param time   time
     * *
     * @param format format
     * *
     * @return String
     */
    fun date2String(time: Long, format: String): String = SimpleDateFormat(format).format(time)





    /**
     * hide keyboard
     */
    fun hide_keyboard_from(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * show keyboard
     */
    fun show_keyboard_from(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 保存Bitmap为图片
     */
    @Throws(Exception::class)
    fun saveBitmap(bitmap: Bitmap, picPath: String) {
        val f = File(picPath + ".jpg")
        if (f.exists()) {
            f.delete()
        }
        try {
            val out = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            bitmap.recycle()
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            bitmap.recycle()
            throw FileNotFoundException()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            bitmap.recycle()
            throw IOException()
        }

    }

}
