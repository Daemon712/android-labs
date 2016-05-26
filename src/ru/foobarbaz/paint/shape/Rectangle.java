package ru.foobarbaz.paint.shape;

import android.graphics.Canvas;

public class Rectangle extends Shape {

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(startX, startY, endX, endY, paint);
    }

    @Override
    public boolean isHit(float x, float y) {
        double xMax = Math.max(startX, endX);
        double xMin = Math.min(startX, endX);
        double yMax = Math.max(startY, endY);
        double yMin = Math.min(startY, endY);
        return (x >= xMin)
                && (x <= xMax)
                && (y >= yMin)
                && (y <= yMax);
    }
}