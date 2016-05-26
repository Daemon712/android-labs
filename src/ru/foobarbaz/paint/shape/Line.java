package ru.foobarbaz.paint.shape;

import android.graphics.Canvas;

public class Line extends Shape {

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    @Override
    public boolean isHit(float x, float y) {
        return (getDistance(x, y, startX, startY) <= 50)
                || (getDistance(x, y, endX, endY) <= 50);
    }

    @Override
    public void changePosition(float x, float y) {
        if (getDistance(x, y, startX, startY) <= 50) {
            startX = x;
            startY = y;
        } else if (getDistance(x, y, endX, endY) <= 50) {
            endX = x;
            endY = y;
        }
    }
}