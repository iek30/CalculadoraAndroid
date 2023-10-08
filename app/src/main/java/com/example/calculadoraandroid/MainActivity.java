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
    public void clickNumeros(View v){
        Button btn = (Button) v;
        tv = findViewById(R.id.lblPantalla);

        if (cadena.equals("") == false){
            if (oculta.charAt(oculta.length()-1) == '.' && btn.getText().equals(".")){
                Toast.makeText(this,"No puedes poner dos '.' seguidos.",Toast.LENGTH_LONG);
            }
            else{
                cadena += btn.getText();
                oculta += btn.getText();
                tv.setText(cadena);

                //DESBLOQUEO SIGNOS
                btn = findViewById(R.id.btnDiv);
                btn.setEnabled(true);
                btn = findViewById(R.id.btnIgual);
                btn.setEnabled(true);
                btn = findViewById(R.id.btnMas);
                btn.setEnabled(true);
                btn = findViewById(R.id.btnMenos);
                btn.setEnabled(true);
                btn = findViewById(R.id.btnPor);
                btn.setEnabled(true);
                btn = findViewById(R.id.btnPunto);
                btn.setEnabled(true);
            }
        }
        else{

            cadena += btn.getText();
            oculta += btn.getText();
            tv.setText(cadena);

            //DESBLOQUEO SIGNOS
            btn = findViewById(R.id.btnDiv);
            btn.setEnabled(true);
            btn = findViewById(R.id.btnIgual);
            btn.setEnabled(true);
            btn = findViewById(R.id.btnMas);
            btn.setEnabled(true);
            btn = findViewById(R.id.btnMenos);
            btn.setEnabled(true);
            btn = findViewById(R.id.btnPor);
            btn.setEnabled(true);
            btn = findViewById(R.id.btnPunto);
            btn.setEnabled(true);
        }

    }

    public void clickSignos(View v){
        Button btn = (Button) v;
        if (btn.getText().equals("X")) oculta += "*";
        else oculta += btn.getText();
        cadena = "";
        tv = findViewById(R.id.lblPantalla);
        tv.setText(evaluarExpresion(oculta) + "");

        //BLOQUEO LOS SIGNOS
        btn = findViewById(R.id.btnDiv);
        btn.setEnabled(false);
        btn = findViewById(R.id.btnIgual);
        btn.setEnabled(false);
        btn = findViewById(R.id.btnMas);
        btn.setEnabled(false);
        btn = findViewById(R.id.btnMenos);
        btn.setEnabled(false);
        btn = findViewById(R.id.btnPor);
        btn.setEnabled(false);
        btn = findViewById(R.id.btnPunto);
        btn.setEnabled(false);
    }

    public void mostrarResultado(View v){
        tv = findViewById(R.id.lblPantalla);
        tv.setText(evaluarExpresion(oculta) + "");
        cadena = evaluarExpresion(oculta) + "";
        oculta = evaluarExpresion(oculta) + "";
    }

    public void clear(View v){
        cadena = "";
        oculta = "";
        tv = findViewById(R.id.lblPantalla);
        tv.setText("0");
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