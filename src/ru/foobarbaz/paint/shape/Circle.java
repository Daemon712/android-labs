package ru.foobarbaz.paint.shape;

import android.graphics.Canvas;

public class Circle extends Shape {

    private float centerX;
    private float centerY;
    private float radius;

    @Override
    public void draw(Canvas canvas) {
        float xMin = Math.min(startX, endX);
        float yMin = Math.min(startY, endY);
        centerX = xMin + Math.abs(endX - startX) / 2;
        centerY = yMin + Math.abs(endY - startY) / 2;
        radius = (float) getDistance(centerX, centerY, endX, endY);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    @Override
    public boolean isHit(float x, float y) {
        double xmax = Math.max(startX, endX);
        double xmin = Math.min(startX, endX);
        double ymax = Math.max(startY, endY);
        double ymin = Math.min(startY, endY);
        return (x >= xmin)
                && (x <= xmax)
                && (y >= ymin)
                && (y <= ymax);
    }
}