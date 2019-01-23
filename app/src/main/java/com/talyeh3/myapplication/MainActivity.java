package com.talyeh3.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button openDialogButtun;
    private TextView average1,average2,average3,average4,title;
    private RelativeLayout p1,p2,p3,p4;
    private ImageView im1;
    private LinearLayout linear1;
    private Dialog dialog;
    private double[] weeks;// Weekly average number
    private String [] stringTotalHours; // Weekly average string like 4h, 3m
    private LineView mLineView,mLineView2,mLineView3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        openDialogButtun = (Button) findViewById( R.id.openDialogButtun );

        dialog= new Dialog(this);
        weeks = new double[4];
        // ~~~~~~~~~~~~~~~~~~~~ the hours in Days~~~~~~~~~~~~~~~~~~~~
        weeks[0] = doAvarage(2, 4, 2, 2, 2, 2, 2);
        weeks[1] = doAvarage(9, 9, 9, 9, 5, 6, 9);
        weeks[2] = doAvarage(14, 14, 14, 14, 14, 14, 14);
        weeks[3] = doAvarage(3, 3,3,3,3, 3,4);

        stringTotalHours = new String [4];
        stringTotalHours[0] = totalHours( weeks[0] );
        stringTotalHours[1] = totalHours( weeks[1] );
        stringTotalHours[2] = totalHours( weeks[2] );
        stringTotalHours[3] = totalHours( weeks[3] );

        openDialogButtun.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
                dialog.show();
            }
        } );
    }
    // *********************************** Calculate the weekly average number *******************************************************
    private double doAvarage(double d1, double d2, double d3, double d4, double d5, double d6, double d7) {
        double sum = ((d1 + d2 + d3 + d4 + d5 +d6 +d7)/7);
        return sum;
    }
    // *********************************** Calculate the weekly average String*******************************************************
    private String totalHours(double sum) {
        double total = sum * 60 ;
        int totalMinutes = (int)total;
        int hours = (int)totalMinutes / 60; //since both are ints, you get an int
        int minutes = (int)totalMinutes % 60;
        return "" + hours + "h, " + minutes + "m";
    }

    public void openDialog() {
        // *** initialization ****
        dialog.setContentView(  R.layout.dialog_statistics );
        dialog.setCancelable( true );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));

        p1 = (RelativeLayout) dialog.findViewById( R.id.p1);
        p2 = (RelativeLayout) dialog.findViewById( R.id.p2);
        p3 = (RelativeLayout) dialog.findViewById( R.id.p3);
        p4 = (RelativeLayout) dialog.findViewById( R.id.p4);

        im1 = (ImageView) dialog.findViewById( R.id.im1);

        average1 = (TextView) dialog.findViewById( R.id.average1);
        average2 = (TextView) dialog.findViewById( R.id.average2);
        average3 = (TextView) dialog.findViewById( R.id.average3);
        average4 = (TextView) dialog.findViewById( R.id.average4);

        linear1 = (LinearLayout) dialog.findViewById( R.id.linear1);
        mLineView = (LineView) dialog.findViewById(R.id.lineView);
        mLineView2 = (LineView) dialog.findViewById(R.id.lineView2);
        mLineView3 = (LineView) dialog.findViewById(R.id.lineView3);

        int margin = 15;
        p1.setY( (int)weeks[0] * -margin );
        p2.setY( (int)weeks[1] * -margin );
        p3.setY( (int)weeks[2] * -margin );
        p4.setY( (int)weeks[3] * -margin );


        average1.setText(  stringTotalHours[0] );
        average2.setText(  stringTotalHours[1] );
        average3.setText(  stringTotalHours[2] );
        average4.setText(  stringTotalHours[3] );

// ****** Drawing lines ***************
        final ViewTreeObserver observer= linear1.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float temp = lessMarginText ( p1.getY());
                PointF pointA = new PointF(p1.getX()+p1.getWidth()/2,p1.getY()+p1.getHeight()/2);
                PointF pointB = new PointF(p2.getX()+p2.getWidth()/2,p2.getY()+p2.getHeight()/2);
                mLineView.setPointA(pointA);
                mLineView.setPointB(pointB);
                mLineView.draw();

                PointF pointC = new PointF(p2.getX()+p2.getWidth()/2,p2.getY()+p2.getHeight()/2);
                PointF pointD = new PointF(p3.getX()+p3.getWidth()/2,p3.getY()+p3.getHeight()/2 );

                mLineView2.setPointA(pointC);
                mLineView2.setPointB(pointD);
                mLineView2.draw();

                PointF pointE = new PointF(p3.getX()+p3.getWidth()/2,p3.getY()+p3.getHeight()/2 );
                PointF pointF = new PointF(p4.getX()+p4.getWidth()/2,p4.getY()+p4.getHeight()/2);

                mLineView3.setPointA(pointE);
                mLineView3.setPointB(pointF);
                mLineView3.draw();
                linear1.getViewTreeObserver().removeOnGlobalLayoutListener( this );
            }
        });
    }
    // ****************** Reduces the spacing between image and text *******************************
    float lessMarginText ( float temp) {
        int sum = 0;
        if (temp <= 0 && temp >= -40)
        {
            sum = -100;
        } else if (temp <= -41 && temp >= -80) {
            sum = -40;
        }  else if (temp <= -150 && temp >= -200) {
            sum = 60;
        }  else if (temp <= -201 &&temp >= -240) {
            sum = 110;
        }
        return sum;
    }
}
