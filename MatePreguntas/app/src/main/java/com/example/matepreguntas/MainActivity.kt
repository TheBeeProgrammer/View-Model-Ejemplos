package com.example.matepreguntas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.matepreguntas.model.MatePreguntas
import com.example.matepreguntas.viewmodel.MatePreguntasViewModel

private const val REQUEST_CODE_TRAMPA = 0

class MainActivity : AppCompatActivity() {
    
    private lateinit var btnVerdad: Button
    private lateinit var btnFalso: Button
    private lateinit var btnSiguiente: Button
    private lateinit var btnAtras: Button
    private lateinit var btnTrampa: Button

    private lateinit var tvPregunta: TextView

    private val quizViewModel: MatePreguntasViewModel by lazy {
        ViewModelProviders.of(this).get(MatePreguntasViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnVerdad = findViewById(R.id.btn_verdadero)
        btnFalso = findViewById(R.id.btn_falso)
        btnSiguiente = findViewById(R.id.btn_siguiente)
        btnAtras = findViewById(R.id.btn_atras)
        btnTrampa = findViewById(R.id.btn_trampa)
        tvPregunta=findViewById(R.id.tv_pregunta)
        tvPregunta.setText(quizViewModel.preguntaActual)

        btnVerdad.setOnClickListener {
            showMessage(quizViewModel.checkRespuesta(true))
            statusButtons(quizViewModel.checkRespuestaPregunta())
            mostrarPuntaje()
        }

        btnFalso.setOnClickListener {
            showMessage(quizViewModel.checkRespuesta(false))
            statusButtons(quizViewModel.checkRespuestaPregunta())
            mostrarPuntaje()
        }

        btnTrampa.setOnClickListener {
            goToTrampaActivity()
        }

        tvPregunta.setOnClickListener {
            quizViewModel.irSiguiente()
            quizViewModel.actualizarPregunta(tvPregunta)
            statusButtons(quizViewModel.checkRespuestaPregunta())
        }

        btnSiguiente.setOnClickListener {
            quizViewModel.irSiguiente()
            quizViewModel.actualizarPregunta(tvPregunta)
            statusButtons(quizViewModel.checkRespuestaPregunta())
        }
        btnAtras.setOnClickListener {
            quizViewModel.irAnterior()
            quizViewModel.actualizarPregunta(tvPregunta)
            statusButtons(quizViewModel.checkRespuestaPregunta())
        }

    }

    private fun statusButtons(status: Boolean) {
        if (status) {
            btnVerdad.visibility = View.GONE
            btnFalso.visibility = View.GONE
        } else {
            btnVerdad.visibility = View.VISIBLE
            btnFalso.visibility = View.VISIBLE
        }
    }

    private fun showMessage(message: Int) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun mostrarPuntaje() {
        if (quizViewModel.preguntas.size == 5) {
            Toast.makeText(
                this,
                " Your Score: ${quizViewModel.calcularPuntje()}%",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun goToTrampaActivity() {
        val respuestaEsVerdad = quizViewModel.respuestaActual
        val intent = TrampaActivity.newIntent(this, respuestaEsVerdad)
        startActivityForResult(intent, REQUEST_CODE_TRAMPA)
    }

    //VALIDAMOS SI EL USUIARIO HIZO TRAMPA O NO
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_TRAMPA) {
            // Elvis Operator if data is null return false else return the value of data
            quizViewModel.esTrampa = data?.getBooleanExtra(EXTRA_RESPUESTA_MOSTRAR, false) ?: false
        }


    }

}
