package com.example.matepreguntas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


private const val EXTRA_RESPUESTA_ES_VERDAD =
    "com.example.matepreguntas.answer_is_true"


const val EXTRA_RESPUESTA_MOSTRAR =
    "com.example.matepreguntas.answer_shown"

class TrampaActivity : AppCompatActivity() {

    private lateinit var tvRespuesta:TextView
    private lateinit var btnRespuesta:Button

    private var respuestaEsVerdad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trampa)
        btnRespuesta=findViewById(R.id.btn_respuesta)
        tvRespuesta=findViewById(R.id.tv_respuesta)
        loadExtras()
        btnRespuesta.setOnClickListener{
            showAnswer()
            setShownAnswerResult(true)
        }
    }
    private fun showAnswer() {

        val answerText = when {
            respuestaEsVerdad -> R.string.btn_verdadero
            else -> R.string.btn_falso
        }
        tvRespuesta.setText(answerText)
    }

    private fun loadExtras() {
        respuestaEsVerdad = intent.getBooleanExtra(EXTRA_RESPUESTA_ES_VERDAD, false)
    }
    private fun setShownAnswerResult(esTrampa:Boolean){
        val data = intent.apply {
            putExtra(EXTRA_RESPUESTA_MOSTRAR,esTrampa)
        }
        setResult(Activity.RESULT_OK,data)
    }
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, TrampaActivity::class.java).apply {
                putExtra(EXTRA_RESPUESTA_ES_VERDAD, answerIsTrue)
            }
        }
    }
}
