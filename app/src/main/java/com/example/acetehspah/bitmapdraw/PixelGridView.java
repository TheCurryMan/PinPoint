package com.example.acetehspah.bitmapdraw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.os.Handler;

import com.couchbase.todolite.LoginActivity;
import com.couchbase.todolite.Splash;

import java.util.ArrayList;

/**
 * Created by acetehspah on 1/14/17.
 */


public class PixelGridView extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private int[][] points;
    private int pointsI;
    private static int pointsM = 150;
    private Paint blackPaint = new Paint();
    private Paint redPaint = new Paint();
    private Paint[] pathPaint;
    private double[] pathScale;
    private static double[] pathScaleStatic = {1.33, 1.66, 2, 1.66, 1.33};
    private Paint[] pointPaint = new Paint[pointsM];
    private byte[][] map;
    private int[][] path;
    private boolean first = true;
    private int pathi;
    private int[][] pathEntire;
    private int pathEntireI;
    private Paint grayPaint = new Paint();
    private int time = 0;
    private int timeM = 4;
    private int pulsei;
    private static int pulsem = 40;
    private int[] pulseAdd = new int[pulsem];
    private Paint[] pulsePaint = new Paint[pulsem];
    private ArrayList<String> allNames;
    private int startH;
    private int third;
    private static int listM = 50;
    private int[] listOffsets = new int[listM];
    private int listI;
    private Paint whitePaint = new Paint();
    private static int gradientM = 50;
    private int gradHeight;
    private Paint gradPaint = new Paint();
    private boolean buttonDown = false;
    private AppCompatActivity home;


    public PixelGridView(Context context) {
        this(context, null);
    }




    public PixelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setTextSize(60);
        blackPaint.setTextAlign(Paint.Align.CENTER);
        blackPaint.setStrokeWidth(10);
        gradPaint.setARGB(255, 0, 0, 0);
        whitePaint.setARGB(255, 235, 235, 235);
        redPaint.setARGB(255, 214, 169, 121);
        for(int i = 0; i < pointPaint.length; i++){
            pointPaint[i] = new Paint();
            pointPaint[i].setARGB(255 - i*255/pointPaint.length, 0, 169, 214);
        }
        grayPaint.setARGB(255, 66, 66, 66);
        for(int i = 0; i < pulsem; i++){
            pulseAdd[i] = i * 3;
            pulsePaint[i] = new Paint();
            pulsePaint[i].setARGB(150 - i*150/pulsem, 0, 169, 214);
        }
        startH = getHeight()/2;
        third = getHeight()/6;
    }

    public void setAllNames(ArrayList<String> names){
        allNames = names;
    }

    public void setHome(AppCompatActivity hom){
        home = hom;
    }

    public void setPath(int[][] pa) {
        path = pa;
        pathPaint = new Paint[path.length];
        for(int i = 0; i < pathPaint.length; i++){
            pathPaint[i] = new Paint();
            //pathPaint[i].setARGB(255, 0, 255, 0);
            pathPaint[i].setARGB(255 - (i * 255/(pathPaint.length)), 0, 169, 214);
        }
        pathi = 0;
    }

    public void setPathEntire(int[][] pa){
        pathEntire = pa;
        int[][] temp = new int[points.length - 1][2];
        for(int i = 0; i < temp.length ; i++){
            temp[i] = points[i+1];
        }
        points = temp;
        for(int i = 0; i < pathEntire.length; i++)
        {
            if(pathEntire[i][0] == points[0][0] && pathEntire[i][1] == points[0][1]){
                path = new int[i + 1][2];
                pathEntireI = i;
                i = pathEntire.length;
            }
        }
        System.out.println(pathEntireI);
        for(int i = 0; i < path.length; i++)
        {
            path[i] = pathEntire[i];
        }
        pathPaint = new Paint[path.length];
        for(int i = 0; i < pathPaint.length; i++){
            pathPaint[i] = new Paint();
            //pathPaint[i].setARGB(255, 0, 255, 0);
            pathPaint[i].setARGB(255 - (i * 150/(pathPaint.length)), 0, 169, 214);
        }
        pathScale = new double[path.length];
        for(int i = 0; i < pathScale.length; i++)
        {
            if(i < 5)
            {
                pathScale[i] = pathScaleStatic[i];
            }
            else
            {
                pathScale[i] = 1;
            }
        }
        pathi = 0;
        pulsei = 0;
    }

    public int getNumColumns() {
        return numColumns;
    }


    public int getNumRows() {
        return numRows;
    }

    public void setPoints(int[][] poi){
        points = poi;

    }

    public void setMap(byte[][] ma){
        map = ma;
        numRows = map.length;
        numColumns = map[0].length;
        calculateDimensions();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = cellWidth;
        startH = numRows * cellHeight;
        third = (getHeight() - startH)/3;
        for(int i = 0; i < listOffsets.length; i++)
        {
            listOffsets[i] = third * i / listOffsets.length;
        }
        listI = listOffsets.length;
        gradHeight = 2*third/gradientM;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), whitePaint);
        if(allNames != null) {
            drawList(canvas);
        }
        if(map != null){
            drawLayout(canvas);
        }
        if(pathEntire != null){
            drawPathEntire(canvas);
        }
        if(path != null) {
            try {
                drawPath(canvas);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(points != null) {
            drawPoints(canvas);
            drawPulse(canvas);
        }
        invalidate();
    }

    private void drawList(Canvas canvas) {
        if(listI >= listOffsets.length)
        {
            canvas.drawText(allNames.get(1), getWidth()/2, startH + third/2 + 20, blackPaint);
            canvas.drawText(allNames.get(2), getWidth()/2, startH + third*3/2 + 20, blackPaint);
            canvas.drawLine(0, startH, getWidth(), startH, blackPaint);
            canvas.drawLine(0, startH + third, getWidth(), startH + third, blackPaint);
            canvas.drawLine(0, startH + 2 * third, getWidth(), startH + 2 * third, blackPaint);
        }
        else {
            canvas.drawText(allNames.get(0), getWidth()/2, startH + third/2 - listOffsets[listI] + 20, blackPaint);
            canvas.drawText(allNames.get(1), getWidth()/2, startH + third*3/2 - listOffsets[listI] + 20, blackPaint);
            canvas.drawText(allNames.get(2), getWidth()/2, startH + third * 5/2 - listOffsets[listI] + 20, blackPaint);
            canvas.drawLine(0, startH - listOffsets[listI], getWidth(), startH - listOffsets[listI], blackPaint);
            canvas.drawLine(0, startH + third - listOffsets[listI], getWidth(), startH + third - listOffsets[listI], blackPaint);
            canvas.drawLine(0, startH + 2 * third - listOffsets[listI], getWidth(), startH + 2 * third - listOffsets[listI], blackPaint);
            canvas.drawLine(0, startH + 3 * third - listOffsets[listI], getWidth(), startH + 3 * third - listOffsets[listI], blackPaint);
            listI++;
        }


        for(int i = 0; i <= gradientM; i++)
        {
            gradPaint.setAlpha(255 - i * 255/gradientM);
            canvas.drawRect(0, getHeight() - i * gradHeight, getWidth(), getHeight() - (i + 1) * gradHeight, gradPaint);
        }
        if(buttonDown){
            //redPaint.setARGB(255, 194, 149, 101);
            pointPaint[0].setARGB(255, 0, 139, 194);
        }
        canvas.drawRoundRect( 300, startH + 2 * third + 50, getWidth() - 300, getHeight() - 50, 100, 100, pointPaint[0]);
        //redPaint.setARGB(255, 214, 169, 121);
        pointPaint[0].setARGB(255, 0, 169, 214);
        if(points.length > 1)
        {
            canvas.drawText("Next", getWidth()/2, startH + third * 5/2 + 20, blackPaint);
        }else
        {
            canvas.drawText("Done", getWidth()/2, startH + third * 5/2 + 20, blackPaint);
        }



    }

    private void drawPulse(Canvas canvas){
        if(path.length < 3 && pulsei >= pulsem){
            pulsei = 0;
        }
        else if(pathi == path.length - 2)
        {
            pulsei = 0;
        }
        if(pulsei < pulsem)
        {
            int x = points[0][0];
            int y = points[0][1];
            canvas.drawCircle(y * cellWidth+cellWidth/2, x*cellHeight + cellHeight/2, cellHeight/4 + pulseAdd[pulsei], pulsePaint[pulsei]);
            pulsei++;
        }
        //if(pulsei >= pulsem){pulsei -= pulsem;}
    }

    private void drawPathEntire(Canvas canvas){
        int x;
        int y;
        for(int i = 0; i < pathEntire.length - pathEntireI - 1; i++)
        {
            x = pathEntire[i + pathEntireI + 1][0];
            y = pathEntire[i + pathEntireI + 1][1];
            canvas.drawCircle(y *cellWidth + cellWidth/2, x * cellHeight + cellHeight/2, cellWidth/4, grayPaint);
            //canvas.drawRect(y * cellWidth + cellWidth/4, x * cellHeight + cellHeight/4, (y+1) * cellWidth - cellWidth/4, (x+1) * cellHeight - cellHeight/4, grayPaint);
        }
    }

    private void drawPoints(Canvas canvas){
        int x;
        int y;
        int index;
        for(int i = 0; i < points.length; i++) {
            x = points[i][0];
            y = points[i][1];
            index = pointsI + i * 35;
            index %= pointsM;
            index = 0; //no blink
            canvas.drawCircle(y *cellWidth + cellWidth/2, x * cellHeight + cellHeight/2, cellWidth/2, pointPaint[index]);
            //canvas.drawRect(y * cellWidth, x * cellHeight, (y+1) * cellWidth, (x+1) * cellHeight, pointPaint[index]);
        }
        pointsI++;
        pointsI %= pointsM;
    }


    private void drawPath(Canvas canvas) throws InterruptedException {
        int x;
        int y;
        int index;
        for(int i = 0; i < pathPaint.length; i++) {
            index = pathi - i;
            if(index < 0){
                index += path.length;
            }
            x = path[index][0];
            y = path[index][1];
            canvas.drawCircle(y*cellWidth + cellWidth/2, x*cellHeight + cellHeight/2, (float)(cellWidth/4 * pathScale[i]), pathPaint[i]);
            //canvas.drawRect(y * cellWidth, x * cellHeight, (y+1) * cellWidth, (x+1) * cellHeight, pathPaint[i]);
        }
        if(time == timeM)
        {
            pathi++;
            time = 0;
        }
        time++;
        if(pathi >= path.length){
            pathi -= path.length;
        }



    }


    private void drawLayout(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), startH, whitePaint);

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (map[j][i] == 1) {

                    canvas.drawRect(i * cellWidth, j * cellHeight,(i + 1) * cellWidth, (j + 1) * cellHeight,redPaint);
                    //canvas.drawRoundRect(i * cellWidth, j * cellHeight, (i + 1) * cellWidth, (j + 1) * cellHeight, 5, 5, redPaint);
                } else if(map[j][i] == 2) {
                    canvas.drawRect(i * cellWidth, j * cellHeight,(i + 1) * cellWidth, (j + 1) * cellHeight,grayPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            buttonDown = false;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(event.getY()>startH + third * 2){
                buttonDown = true;
                if(points.length > 1) {

                    allNames.remove(0);

                    int[][] temp = new int[pathEntire.length - pathEntireI - 1][2];
                    for (int i = 0; i < temp.length; i++) {
                        temp[i] = pathEntire[i + pathEntireI + 1];
                    }
                    pathEntire = temp;
                    temp = new int[points.length - 1][2];
                    for (int i = 0; i < temp.length; i++) {
                        temp[i] = points[i + 1];
                    }
                    points = temp;
                    for (int i = 0; i < pathEntire.length; i++) {
                        if (pathEntire[i][0] == points[0][0] && pathEntire[i][1] == points[0][1]) {
                            path = new int[i + 1][2];
                            pathEntireI = i;
                            i = pathEntire.length;
                        }
                    }
                    for (int i = 0; i < path.length; i++) {
                        path[i] = pathEntire[i];
                    }


                    pathPaint = new Paint[path.length];
                    for(int i = 0; i < pathPaint.length; i++){
                        pathPaint[i] = new Paint();
                        //pathPaint[i].setARGB(255, 0, 255, 0);
                        pathPaint[i].setARGB(255 - (i * 150/(pathPaint.length)), 0, 169, 214);
                    }
                    pathScale = new double[path.length];
                    for(int i = 0; i < pathScale.length; i++)
                    {
                        if(i < 5)
                        {
                            pathScale[i] = pathScaleStatic[i];
                        }
                        else
                        {
                            pathScale[i] = 1;
                        }
                    }


                    pathi = 0;
                    pulsei = 0;
                    listI = 0;

                    invalidate();
                }
                else {
                    Intent mainIntent = new Intent(home,LoginActivity.class);
                    home.startActivity(mainIntent);
                    home.finish();
                }
            }
        }

        return true;
    }

}
