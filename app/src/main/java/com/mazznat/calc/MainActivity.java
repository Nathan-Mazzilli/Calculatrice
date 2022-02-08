package com.mazznat.calc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public enum Ops {
        PLUS("+"),
        MOINS("-"),
        FOIS("*"),
        DIV("/"),
        DOT("."),
        POURCENT("%");

        private final String name;
        Ops(String name) {
            this.name = name;
        }
    }


    private TextView screen;
    private float op1=0;
    private float op2=0;
    private Ops operator=null;
    private boolean isOp1=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button BT_Point = findViewById(R.id.BT_Point);
        screen = (TextView) findViewById(R.id.main_tv_calc);
        Button btnEgal = (Button)findViewById(R.id.BT_Egal);
        btnEgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = screen.getText().toString();
                String[] valueString = s.split("[+/%\\*]");
                if (valueString[1] == null) { screen.setText("Error"); }
                if (valueString[0].equals(".") || valueString[1].equals(".")) { screen.setText("Error"); } else {
                    op1 = Float.parseFloat(valueString[0]);
                    op2 = Float.parseFloat(valueString[1]);
                    Log.v("Nombre1", String.valueOf(op1));
                    Log.v("Nombre2", String.valueOf(op2));
                    compute();
                    BT_Point.setEnabled(true);
                }
            }
        });

        ImageButton BT_Clear = findViewById(R.id.BT_Reset);
        BT_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                BT_Point.setEnabled(true);
            }
        });

        Button BT_Pourcent = findViewById(R.id.BT_Pourcent);
        BT_Pourcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operator = Ops.POURCENT;
                compute();
            }
        });
        BT_Point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen.append(".");
                BT_Point.setEnabled(false);
            }
        });

    }


    public void compute() {
        if(isOp1) {
            if (operator == Ops.POURCENT) {
                screen.setText(String.valueOf(op1 /= 100));
            }
        } else {
            switch(operator) {
                case PLUS  : op1 += op2;break;
                case MOINS : op1 -= op2; break;
                case FOIS  : op1 *= op2; break;
                case DIV   : op1 /= op2;break;

                default : return;
            }
            op2 = 0;
            isOp1 = true;
            screen.setText(String.valueOf(op1));
        }
    }

    private void clear() {
        op1 = 0;
        op2 = 0;
        operator = null;
        isOp1 = true;
        screen.setText("");
    }

    public void setOperator(View v) {
        Button BT_Point = findViewById(R.id.BT_Point);
        switch (v.getId()) {
            case R.id.BT_Plus: operator=Ops.PLUS; screen.append("+");BT_Point.setEnabled(true); break;
            case R.id.BT_Moins: operator=Ops.MOINS; screen.append("-");BT_Point.setEnabled(true);  break;
            case R.id.BT_Fois: operator=Ops.FOIS; screen.append("*"); BT_Point.setEnabled(true);  break;
            case R.id.BT_Diviser: operator=Ops.DIV; screen.append("/");BT_Point.setEnabled(true);    break;
            case R.id.BT_Point: operator= Ops.DOT; screen.append(".");BT_Point.setEnabled(true);  break;
            case R.id.BT_Pourcent: operator= Ops.POURCENT;BT_Point.setEnabled(true);  break;
            default : return;
        }
        isOp1=false;
    }

    public void addNumber(View v){
        try {
            int val = Integer.parseInt(((Button)v).getText().toString());

            screen.append(String.valueOf(val));
        }catch (NumberFormatException | ClassCastException e) {
        }
    }

}