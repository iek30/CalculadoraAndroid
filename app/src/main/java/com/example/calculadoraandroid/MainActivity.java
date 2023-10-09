package com.example.calculadoraandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private TextView tv;
    private String cadena = "";
    private String oculta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //EVENTOS DE LOS NÚMEROS.
    public void clickNumeros(View v){
        Button btn = (Button) v;
        tv = findViewById(R.id.lblPantalla);

        if (btn.getText().equals(".")) btn.setEnabled(false);

        cadena += btn.getText();
        oculta += btn.getText();
        tv.setText(cadena);

        habilitarSignos(true);
    }

    //EVENTOS DE LOS SIGNOS.
    public void clickSignos(View v){
        Button btn = (Button) v;
        if (btn.getText().equals("X")) oculta += "*";
        else oculta += btn.getText();
        cadena = "";
        tv = findViewById(R.id.lblPantalla);
        tv.setText(evaluarExpresion(oculta) + "");

        habilitarSignos(false);
        btn = findViewById(R.id.btnPunto);
        btn.setEnabled(true);
    }

    //EVENTO DEL SIGNO "=".
    public void mostrarResultado(View v){
        tv = findViewById(R.id.lblPantalla);
        tv.setText(evaluarExpresion(oculta) + "");
        cadena = evaluarExpresion(oculta) + "";
        oculta = evaluarExpresion(oculta) + "";
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