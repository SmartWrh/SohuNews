package com.wrh.sohunews;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity implements ViewPager.OnPageChangeListener,ViewpagerIndicator.OnTextClick{

    private ViewPager viewPager;
    private int lastX = 0;
    private ViewpagerIndicator indicator;
    private HorizontalScrollView horizontalScrollView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (ViewpagerIndicator) findViewById(R.id.indicator);
        indicator.setOnTextClick(this);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizon);
        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 只有当从第一项滚动第二项或者从第二项滚动第一项时，整体不动指示器单独滚动，其余都是整个布局滚动
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!(position == 0 && positionOffset - lastX >= 0) || !(position == 1 && positionOffset - lastX <= 0)){
            horizontalScrollView.scrollTo((int)((positionOffset+position-1)*indicator.getIndicatorWidth()), 0);
        }
        indicator.scrollTo(position, positionOffset);
        lastX = (int) positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        indicator.resetTextViewColor();
         indicator.setFocusTextView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void textViewClick(int position) {
        viewPager.setCurrentItem(position);
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 13;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText("Text:" + position);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
