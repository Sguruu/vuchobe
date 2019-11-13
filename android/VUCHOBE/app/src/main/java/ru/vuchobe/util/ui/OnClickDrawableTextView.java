package ru.vuchobe.util.ui;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public interface OnClickDrawableTextView extends View.OnTouchListener {

    boolean onClick(View view, Position position, Drawable drawable, MotionEvent event);

    @Override
    default boolean onTouch(View view, MotionEvent event) {
        TextView v = (TextView) view;
        Drawable l = v.getCompoundDrawables()[0];//left
        Drawable t = v.getCompoundDrawables()[1];//top
        Drawable r = v.getCompoundDrawables()[2];//right
        Drawable b = v.getCompoundDrawables()[3];//bottom

        Position position = null;
        float x = event.getX();
        float y = event.getY();

        if (l != null && x < v.getCompoundPaddingLeft()) {
            position = Position.LEFT;
        } else if (r != null && x > v.getMeasuredWidth() - v.getCompoundPaddingRight()) {
            position = (v.getError() == null) ? Position.RIGHT : Position.ERROR;
        } else if (t != null && y < v.getCompoundPaddingTop()) {
            position = Position.TOP;
        } else if (b != null && y > v.getMeasuredHeight() - v.getCompoundPaddingBottom()) {
            position = Position.BOTTOM;
        }
        if (position != null) {
            return onClick(view, position, v.getCompoundDrawables()[position.getPosition()], event);
        }
        return onClick(view, Position.MAIN, null, event);
    }

    enum Position {
        MAIN(-1), LEFT(0), TOP(1), RIGHT(2), BOTTOM(3), ERROR(2);

        private final int position;

        Position(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }
}
