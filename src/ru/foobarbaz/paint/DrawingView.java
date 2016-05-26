package ru.foobarbaz.paint;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import ru.foobarbaz.paint.shape.Circle;
import ru.foobarbaz.paint.shape.Line;
import ru.foobarbaz.paint.shape.Rectangle;
import ru.foobarbaz.paint.shape.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawingView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint(Paint.DITHER_FLAG);
    private List<Shape> shapes = new ArrayList<>();
    private ShapeType currentShapeType = ShapeType.LINE;
    private Mode currentMode = Mode.DRAW;

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
        repaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentMode.fingerDown(this, x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                currentMode.fingerMove(this, x, y);
                break;
            case MotionEvent.ACTION_UP:
                currentMode.fingerUp(this, x, y);
                break;
        }
        repaint();
        invalidate();
        return true;
    }

    private void repaint() {
        canvas.drawColor(Color.WHITE);
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
    }

    public void setShapeType(ShapeType shapeType) {
        currentShapeType = shapeType;
    }

    public void clean() {
        shapes.clear();
        repaint();
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setWidth(float width) {
        paint.setStrokeWidth(width);
    }

    public enum Mode {
        DRAW {
            private Shape shape;

            @Override
            void fingerDown(DrawingView drawingView, float x, float y) {
                shape = drawingView.currentShapeType.createShape();
                drawingView.shapes.add(shape);
                shape.setStartX(x);
                shape.setEndX(x);
                shape.setStartY(y);
                shape.setEndY(y);
                shape.setPaint(drawingView.paint);
            }

            @Override
            void fingerMove(DrawingView drawingView, float x, float y) {
                if (shape != null){
                    shape.setEndY(y);
                    shape.setEndX(x);
                }
            }

            @Override
            void fingerUp(DrawingView drawingView, float x, float y) {
                shape = null;
            }

        },
        MOVE {
            private Shape selectedShape;

            @Override
            void fingerDown(DrawingView drawingView, float x, float y) {
                for (Shape shape : drawingView.shapes) {
                    if (shape.isHit(x, y)) {
                        selectedShape = shape;
                        break;
                    }
                }
            }

            @Override
            void fingerMove(DrawingView drawingView, float x, float y) {
                if (selectedShape != null) {
                    selectedShape.changePosition(x, y);
                }
            }

            @Override
            void fingerUp(DrawingView drawingView, float x, float y) {
                selectedShape = null;
            }

        },
        DELETE {
            @Override
            void fingerDown(DrawingView drawingView, float x, float y) {
                for (Iterator<Shape> it = drawingView.shapes.iterator(); it.hasNext(); ) {
                    if (it.next().isHit(x, y)){
                        it.remove();
                        return;
                    }
                }

            }
        };

        void fingerDown(DrawingView drawingView, float x, float y) { }
        void fingerMove(DrawingView drawingView, float x, float y) { }
        void fingerUp(DrawingView drawingView, float x, float y) { }
    }

    public enum ShapeType {
        LINE {
            @Override
            Shape createShape() {
                return new Line();
            }
        }, RECTANGLE {
            @Override
            Shape createShape() {
                return new Rectangle();
            }
        }, CIRCLE {
            @Override
            Shape createShape() {
                return new Circle();
            }
        };

        abstract Shape createShape();
    }
}