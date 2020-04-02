package com.example.matepreguntas.viewmodel

import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.matepreguntas.R
import com.example.matepreguntas.model.MatePreguntas

class MatePreguntasViewModel : ViewModel() {

    /*Toda Logica En ViewModel*/

    private var currentIndex = 0
    private var preguntasCorrectas = 0
    var preguntas: MutableList<Int> = mutableListOf()
    var esTrampa = false

    private val preguntasLista = listOf(
        MatePreguntas(R.string.primera_pregunta, false),
        MatePreguntas(R.string.segunda_pregunta, true),
        MatePreguntas(R.string.tercera_pregunta, true),
        MatePreguntas(R.string.cuarta_pregunta, true),
        MatePreguntas(R.string.quinta_pregunta, false)
    )

    //Getters Customizados Kotlin

    val respuestaActual: Boolean
        get() = preguntasLista[currentIndex].respuesta

    val preguntaActual: Int
        get() = preguntasLista[currentIndex].textResId

    //Logica Botones Siguiente Anterior

    fun irSiguiente() {
        currentIndex = (currentIndex + 1) % preguntasLista.size
    }

    fun irAnterior() {
        if (currentIndex > 0) currentIndex = (currentIndex - 1) % preguntasLista.size

    }

    fun calcularPuntje() =
        (preguntasCorrectas * 100) / preguntas.size


    fun checkRespuesta(respuestaUsuario: Boolean): Int {
        val respuestaCorrecta = respuestaActual
        preguntas.add(preguntaActual)
        return when {
            esTrampa ->
                R.string.es_trrampa
            respuestaUsuario == respuestaCorrecta ->
                R.string.respuesta_correcta
            else ->
                R.string.respuesta_incorrecta
        }
    }

    // Evita que se conteste mas de dos veces una pregunta

    fun checkRespuestaPregunta(): Boolean {
        var status = false
        preguntas.forEach { questions ->
            if (questions == preguntaActual) {
                status = true
            }
        }
        return status
    }

    fun actualizarPregunta(tvPregunta: TextView) {
        val questionTextResId = preguntaActual
        tvPregunta.setText(questionTextResId)
    }
}