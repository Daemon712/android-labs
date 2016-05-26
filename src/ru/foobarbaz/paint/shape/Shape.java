package ru.foobarbaz.paint.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Shape {

    protected float startX;
    protected float startY;
    protected float endX;
    protected float endY;
    protected Paint paint = new Paint();

    public abstract void draw(Canvas canvas);

    public abstract boolean isHit(float x, float y);

    public void changePosition(float x, float y) {
        if (getDistance(x, y, startX, startY) <= 50) {
            startX = x;
            startY = y;
        } else if (getDistance(x, y, startX, endY) <= 50) {
            startX = x;
            endY = y;
        } else if (getDistance(x, y, endX, endY) <= 50) {
            endX = x;
            endY = y;
        } else if (getDistance(x, y, endX, startY) <= 50) {
            endX = x;
            startY = y;
        } else {
            changeBothX(x);
            changeBothY(y);
        }
    }

    private void changeBothX(float x) {
        float a = startX;
        float b = endX;
        startX = x - (b - a) / 2;
        endX = x + (b - a) / 2;
    }

    private void changeBothY(float y) {
        float a = startY;
        float b = endY;
        startY = y - (b - a) / 2;
        endY = y + (b - a) / 2;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paintToSave) {
        paint.setColor(paintToSave.getColor());
        paint.setStrokeWidth(paintToSave.getStrokeWidth());
        paint.setStyle(paintToSave.getStyle());
    }

    protected double getDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}