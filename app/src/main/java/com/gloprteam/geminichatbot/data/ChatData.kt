package com.gloprteam.geminichatbot.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    val api_key="AIzaSyB5AShOvDJ9N6Mg9eqJe-LEMbJZP9DxTdc"
    suspend fun getResponse(prompt:String):Chat{
        val generativeModel =GenerativeModel(
            modelName="gemini-pro", apiKey = api_key
        )
        try{
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }
            return Chat(
                prompt=response.text?:"error",
                bitmap=null,
                isFromUser = false
            )
        }
        catch (e:ResponseStoppedException){
            return Chat(
                prompt=e.message?:"error",
                bitmap=null,
                isFromUser = false
            )
        }
    }

    suspend fun getResponse(prompt:String,bitmap:Bitmap):Chat{
        val generativeModel =GenerativeModel(
            modelName="gemini-pro-vision", apiKey = api_key
        )
        try{
            val inputContent =content{
                image(bitmap)
                text(prompt)
            }
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }
            return Chat(
                prompt=response.text?:"error",
                bitmap=null,
                isFromUser = false
            )
        }
        catch (e:ResponseStoppedException){
            return Chat(
                prompt=e.message?:"error",
                bitmap=null,
                isFromUser = false
            )
        }
    }
}