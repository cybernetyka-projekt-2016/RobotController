package pg.gda.org.robotcontroller;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    private boolean pagingEnabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.pagingEnabled = false;
    }

    public CustomViewPager(Context context) {
        super(context);
        this.pagingEnabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return pagingEnabled ? super.onTouchEvent(event) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return pagingEnabled ? super.onInterceptTouchEvent(event) : false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.pagingEnabled = enabled;
    }

    public boolean isPagingEnabled() {
        return pagingEnabled;
    }
}
