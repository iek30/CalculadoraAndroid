package com.example.calculadoraandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private TextView tv;
    private String cadena = "";
    private String oculta = "";
    private Boolean primerToquePunto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cambiarModoNocturno(View v){
        Switch s = findViewById(R.id.switchN);
        if(s.isChecked()) {
            findViewById(R.id.lyt).setBackgroundColor(getResources().getColor(R.color.black));
            s.setText("Dark");
            s.setTextColor(getResources().getColor(R.color.white));
        }
        else {
            findViewById(R.id.lyt).setBackgroundColor(getResources().getColor(R.color.white));
            s.setText("Light");
            s.setTextColor(getResources().getColor(R.color.black));
        }
    }

    //EVENTOS DE LOS NÚMEROS.
    public void clickNumeros(View v){
        //Selecciono el TextView donde muestro mi texto.
        Button btn = (Button) v;
        tv = findViewById(R.id.lblPantalla);

        cadena += btn.getText();
        oculta += btn.getText();
        tv.setText(cadena);

        habilitarSignos(true);

    }

    public void clickPunto(View v){
        if (esPosiblePonerPunto()){
            Button btn = (Button) v;
            tv = findViewById(R.id.lblPantalla);

            cadena += btn.getText();
            oculta += btn.getText();
            tv.setText(cadena);
        }
    }

    //EVENTOS DE LOS SIGNOS.
    public void clickSignos(View v){
        Button btn = (Button) v;

        if (btn.getText().equals("-") && esPosiblePonerSignoMenos()){
            tv = findViewById(R.id.lblPantalla);

            cadena += btn.getText();
            oculta += "0-";
            tv.setText(cadena);
        }
        else{
            if (btn.getText().equals("X")) oculta += "*";
            else oculta += btn.getText();
            cadena = "";
            tv = findViewById(R.id.lblPantalla);
            tv.setText(evaluarExpresion(oculta) + "");

            habilitarSignos(false);
            btn = findViewById(R.id.btnPunto);
            btn.setEnabled(true);
        }

    }

    //EVENTO DEL SIGNO "=".
    public void mostrarResultado(View v){
        tv = findViewById(R.id.lblPantalla);
        tv.setText(evaluarExpresion(oculta) + "");
        cadena = evaluarExpresion(oculta) + "";
        oculta = evaluarExpresion(oculta) + "";

        if (oculta.equals("NaN")) {
            cadena = "";
            oculta = "";
            habilitarSignos(false);
        }
    }

    //EVENTO DE "C".
    public void clear(View v){
        cadena = "";
        oculta = "";
        tv = findViewById(R.id.lblPantalla);
        tv.setText("0");
        habilitarSignos(false);
    }




    //FUNCIONES

    public Boolean esPosiblePonerPunto(){
        if(cadena.equals("") || cadena.contains(".")) return false;
        else return true;
    }

    public Boolean esPosiblePonerSignoMenos(){
        if(oculta.equals("")) return true;
        else return false;
    }

    public void habilitarSignos(Boolean flag){
        Button btn;
        btn = findViewById(R.id.btnDiv);
        btn.setEnabled(flag);
        btn = findViewById(R.id.btnIgual);
        btn.setEnabled(flag);
        btn = findViewById(R.id.btnMas);
        btn.setEnabled(flag);
        btn = findViewById(R.id.btnMenos);
        btn.setEnabled(flag);
        btn = findViewById(R.id.btnPor);
        btn.setEnabled(flag);
    }

    public double evaluarExpresion(@NonNull String expresion) {

        // Reemplaza todos los espacios en blanco
        expresion = expresion.replaceAll("\\s+", "");

        // Agrega un "+" al principio si la expresión comienza con "-"
        if (expresion.startsWith("-")) {
            expresion = "0" + expresion;
        }

        String[] tokens = expresion.split("[+\\-*/]");
        String[] operadores = expresion.split("[\\d.]+");

        double resultado = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i++) {
            char operador = operadores[i].charAt(0);
            double numero = Double.parseDouble(tokens[i]);

            switch (operador) {
                case '+':
                    resultado += numero;
                    break;
                case '-':
                    resultado -= numero;
                    break;
                case '*':
                    resultado *= numero;
                    break;
                case '/':
                    if (numero != 0) {
                        resultado /= numero;
                    } else {
                        resultado = Double.NaN;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Operador no válido: " + operador);
            }
        }

        return resultado;
    }
}