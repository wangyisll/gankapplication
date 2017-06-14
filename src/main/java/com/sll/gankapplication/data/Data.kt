/**
 * Copyright 2017 yidong
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package onlyloveyd.com.gankioclient.data

import com.google.gson.annotations.SerializedName
import com.sll.gankapplication.`interface`.MultiTypeData
import java.util.*

data class DailyData(var error: Boolean,
                     var results: ResultData,
                     var category: List<String>)

data class TypeData(var error: Boolean, var results: List<CategoryData>)

data class ResultData(
        @SerializedName("Android")
        var android: List<DetailsData>?,
        @SerializedName("App")
        var app: List<DetailsData>?,
        @SerializedName("iOS")
        var ios: List<DetailsData>?,
        @SerializedName("休息视频")
        var video: List<DetailsData>?,
        @SerializedName("前端")
        var js: List<DetailsData>,
        @SerializedName("拓展资源")
        var res: List<DetailsData>?,
        @SerializedName("瞎推荐")
        var rec: List<DetailsData>?,
        @SerializedName("福利")
        var bonus: List<DetailsData>?)

data class DetailsData(var _id: String,
                       var createdAt: String,
                       var desc: String,
                       var publishedAt: Date,
                       var source: String,
                       var type: String,
                       var url: String,
                       var isUsed: Boolean,
                       var who: String,
                       var images: List<String>):MultiTypeData{
    override fun getType(): Int = 1
}


data class CategoryData(var desc: String,
                        var ganhuo_id: String,
                        var publishedAt: String,
                        var readability: String,
                        var type: String,
                        var url: String,
                        var who: String):MultiTypeData{
    override fun getType(): Int = 2
}


data class EmptyData(val message: String):MultiTypeData{
    override fun getType(): Int = 0
}

data class MindData(var title: String,
                    var author: String?,
                    var time: String?) : MultiTypeData{
    var url: String = ""
        get() = "http://gank.io" + field

    override fun getType(): Int =3
}

data class SearchData(
        @SerializedName("count")
        var count: Int,
        var error: Boolean,
        var results: List<CategoryData>)
