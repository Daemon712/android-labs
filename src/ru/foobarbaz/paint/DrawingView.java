package ru.foobarbaz.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import ru.foobarbaz.paint.shape.Circle;
import ru.foobarbaz.paint.shape.Line;
import ru.foobarbaz.paint.shape.Rectangle;
import ru.foobarbaz.paint.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private float startX;
    private float startY;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint(Paint.DITHER_FLAG);
    private List<Shape> shapes = new ArrayList<>();
    private Shape selectedShape;
    private ShapeType currentShapeType = ShapeType.LINE;

    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fingerDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                fingerMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                fingerUp(x, y);
                break;
        }
        return true;
    }

    private void fingerDown(float x, float y) {
        startX = x;
        startY = y;
        if (currentShapeType == ShapeType.NO) {
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (shapes.get(i).isHit(x, y)) {
                    Shape tempShape = shapes.get(i);
                    shapes.set(i, shapes.get(shapes.size() - 1));
                    shapes.set(shapes.size() - 1, tempShape);
                    break;
                }
            }
        }
        invalidate();
    }

    private void fingerMove(float x, float y) {
        repaint();
        if (currentShapeType == ShapeType.NO) {
            moveShape(x, y);
        } else {
           drawShape(x, y);
        }
        invalidate();
    }

    private void moveShape(float x, float y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).isHit(x, y)) {
                Shape tempShape = shapes.get(i);
                shapes.set(i, shapes.get(shapes.size() - 1));
                shapes.set(shapes.size() - 1, tempShape);
                selectedShape = tempShape;
                break;
            }
        }
        selectedShape.changePosition(x, y);
    }

    private void drawShape(float x, float y) {
        Shape shape = getShape(x, y);
        Paint paintToShape = new Paint();
        paintToShape.setColor(paint.getColor());
        paintToShape.setStrokeWidth(paint.getStrokeWidth());
        paintToShape.setStyle(Paint.Style.STROKE);
        shape.draw(canvas);
    }

    private void repaint() {
        canvas.drawColor(Color.WHITE);
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
    }

    private void fingerUp(float x, float y) {
        if (currentShapeType == ShapeType.NO) {
            return;
        }
        shapes.add(getShape(x, y));
        invalidate();
    }

    private Shape getShape(float finishX, float finishY) {
        Shape result;
        switch (currentShapeType) {
            case LINE:
                result = new Line();
                break;
            case RECTANGLE:
                result = new Rectangle();
                break;
            case CIRCLE:
                result = new Circle();
                break;
            default:
                throw new IllegalArgumentException("Unknown shape.");
        }
        result.setStartX(startX);
        result.setStartY(startY);
        result.setEndX(finishX);
        result.setEndY(finishY);
        result.setPaint(paint);
        return result;
    }

    public void setMode(ShapeType shapeType) {
        currentShapeType = shapeType;
    }

    public void clean() {
        shapes.clear();
        repaint();
    }

    public void removeLast() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setWidth(float width) {
        paint.setStrokeWidth(width);
    }
}