package com.anciar.stickyheader.utils

import android.content.Context
import com.anciar.stickyheader.data.SectionPojo
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonUtils {

    private const val SOURCE_FILE_NAME = "sample_data.json"

    fun getItems(context: Context): List<SectionPojo> {

        val jsonString: String
        try {
            jsonString =
                context.assets.open(SOURCE_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }

        return GsonBuilder().create()
            .fromJson(jsonString, object : TypeToken<List<SectionPojo>>() {}.type)
    }
}