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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button openDialogButtun;
    private TextView average1,average2,average3,average4;
    private RelativeLayout p1,p2,p3,p4;
    private LinearLayout linear1;
    private Dialog dialog;
    private double[] weeks;// Weekly average number
    private String [] stringTotalHours; // Weekly average string like 4h, 3m
    private LineView mLineView1,mLineView2,mLineView3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        openDialogButtun = (Button) findViewById( R.id.openDialogButtun );
        dialog= new Dialog(this);
        weeks = new double[4];

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // ~~~~~~~~~~~~~~~~~~~~ the hours in Days~~~~~~~~~~~~~~~~~~~~~~~
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        weeks[0] = doAverage(2, 3, 2, 3, 0, 0, 0);
        weeks[1] = doAverage(10, 10, 10, 14, 14, 14, 4);
        weeks[2] = doAverage(18, 18, 18, 18, 18, 18, 18);
        weeks[3] = doAverage(6, 6,8,3,3, 3,4);

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
    private double doAverage(double d1, double d2, double d3, double d4, double d5, double d6, double d7) {
        double sum = ((d1 + d2 + d3 + d4 + d5 +d6 +d7)/7);
        if (sum > 24 ) {
            sum = 24;
        } else if (sum <0) {
            sum = 0 ;
        }
        return sum;
    }
    // *********************************** Calculate the weekly average String*******************************************************
    private String totalHours(double sum) {
        double total = sum * 60 ;
        int totalMinutes = (int)total;
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
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

        average1 = (TextView) dialog.findViewById( R.id.average1);
        average2 = (TextView) dialog.findViewById( R.id.average2);
        average3 = (TextView) dialog.findViewById( R.id.average3);
        average4 = (TextView) dialog.findViewById( R.id.average4);

        linear1 = (LinearLayout) dialog.findViewById( R.id.linear1);
        mLineView1 = (LineView) dialog.findViewById(R.id.lineView);
        mLineView2 = (LineView) dialog.findViewById(R.id.lineView2);
        mLineView3 = (LineView) dialog.findViewById(R.id.lineView3);

        //Position the circles according to average hours
        int margin = 15;
        p1.animate().translationYBy( (int)weeks[0] * -margin  ).setDuration( 2000 );
        p2.animate().translationYBy( (int)weeks[1] * -margin  ).setDuration( 2000 );
        p3.animate().translationYBy( (int)weeks[2] * -margin  ).setDuration( 2000 );
        p4.animate().translationYBy( (int)weeks[3] * -margin  ).setDuration( 2000 );

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
                drawLines(p1, p2 ,mLineView1);
                drawLines(p2, p3 ,mLineView2);
                drawLines(p3, p4 ,mLineView3);
                linear1.getViewTreeObserver().removeOnGlobalLayoutListener( this );
            }

            public void drawLines(android.widget.RelativeLayout p1, android.widget.RelativeLayout p2 ,LineView mLineView) {
                PointF pointA = new PointF(p1.getX()+p1.getWidth()/2,p1.getY()+p1.getHeight()/2);
                PointF pointB = new PointF(p2.getX()+p2.getWidth()/2,p2.getY()+p2.getHeight()/2);
                mLineView.animate().alpha( 1f ).setDuration( 2000 );
                mLineView.setPointA(pointA);
                mLineView.setPointB(pointB);
                mLineView.draw();
            }
        });
    }
}