package com.example.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.chatbot.R
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var editTextInput: EditText
    lateinit var editTextOutput: EditText

    lateinit var chat: Chat
    var stringBuilder: StringBuilder = java.lang.StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextInput = findViewById(R.id.editTextInput)
        editTextOutput = findViewById(R.id.editTextOutput)

        val generativeModel = GenerativeModel(
            // For text-only input, use the gemini-pro model
            modelName = "gemini-1.5-flash",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = "AIzaSyA5bDdDwFsZ86NXGMpNMdw47bsKIF2dPzU"
        )
        chat = generativeModel.startChat(
            history = listOf(
                content(role = "user") { text("Hallo aku assistant nya glenn") },
                content(role = "model") { text("Salam kenal ya. ada yang bisa saya bantu?") }
            )
        )
//        stringBuilder.append("Hallo aku assistant nya glenn\n\n")
//        stringBuilder.append("Salam kenal ya. ada yang bisa saya bantu?\n\n")

        editTextOutput.setText(stringBuilder.toString())
    }
    public fun buttonSendChat(view: View) {
        val userInput = editTextInput.text.toString()
        stringBuilder.append("user: $userInput\n\n")

        MainScope().launch {
            val result = chat.sendMessage(userInput)
            stringBuilder.append("chatbot: ${result.text}\n\n")

            editTextOutput.setText(stringBuilder.toString())
            editTextInput.setText("")
        }
    }
}