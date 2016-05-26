package ru.foobarbaz.paint.shape;

import android.graphics.Canvas;

public class Circle extends Shape {

    @Override
    public void draw(Canvas canvas) {
        float xMin = Math.min(startX, endX);
        float yMin = Math.min(startY, endY);
        float centerX = xMin + Math.abs(endX - startX) / 2;
        float centerY = yMin + Math.abs(endY - startY) / 2;
        float radius = (float) getDistance(centerX, centerY, endX, endY);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    @Override
    public boolean isHit(float x, float y) {
        float xMin = Math.min(startX, endX);
        float yMin = Math.min(startY, endY);
        float centerX = xMin + Math.abs(endX - startX) / 2;
        float centerY = yMin + Math.abs(endY - startY) / 2;
        float radius = (float) getDistance(centerX, centerY, endX, endY);
        float dist2point = (float) getDistance(centerX, centerY, x, y);
        return dist2point < radius;
    }
}