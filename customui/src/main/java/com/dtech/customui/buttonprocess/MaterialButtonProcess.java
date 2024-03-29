package com.dtech.customui.buttonprocess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import com.dtech.customui.R;

public class MaterialButtonProcess extends LinearLayout /*implements ProgressButtonListener, View.OnClickListener*/ {
    private RelativeLayout button_click_parent;
    private CardView button_root;
    private ImageView button_icon;
    private ProgressBar button_progress;
    private TextView button_text;
    OnClickListener listener;
    private ProgressButtonListener listenerprogress;
    private String currentText;

    public void setProgressButtonListener(ProgressButtonListener listener) {
        this.listenerprogress = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MaterialButtonProcess(Context context) {
        super(context);
        init(context, null, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MaterialButtonProcess(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MaterialButtonProcess(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaterialButtonProcess(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }*/

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            //setText("tesss");
            if (!isClickable())setClickable(true);
            listenerprogress.onStartProgress();
            start();
            return performClick();
        }
        return true;
    }
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MaterialButtonProcess,
                0, 0);
        View view = inflate(getContext(), R.layout.material_process_button, null);
        addView(view);
        //setOnClickListener((OnClickListener) this);
        setType(view);
        /*todo: last activity*/
        if (/*typedValue.resourceId != 0*/this.getBackground() == null) {
            Log.d("LIB", "init: user not set bg");
            //setBackgroundroot(getResources().getDrawable(R.drawable.rounded2, null));

            setMBColor(getResources().getColor(android.R.color.holo_green_dark));
            //button_root.setCardBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        } else {
            Log.d("LIB", "init: user set bg");
            //int color = Color.TRANSPARENT;
            Drawable background = this.getBackground();

            //button_root.setBackground(background);
            if (background instanceof ColorDrawable) {
                //this.setBackgroundColor(Color.TRANSPARENT);
                int color = ((ColorDrawable) background).getColor();
                this.setBackground(null);
                Log.d("LIB", "init: get colorDrawable->" + color);
                setMBColor(color);
                //return;
            } else {
                if (background instanceof Drawable) {

                    setBackgroundroot(background);
                }
                Log.d("LIB", "init: not get colorDrawable" +background.getState());
            }  /*else if (background instanceof Drawable) {

            }*/

        }
        for (int i=0; i<a.getIndexCount();i++){
            int attr = a.getIndex(i);
            if (attr == R.styleable.MaterialButtonProcess_text) {
                setText(a.getString(attr));
            } /*else if (attr == R.styleable.MaterialButtonProcess_backgroundColor) {
                setBackgroundColor(a.getColorStateList(attr));
            }*/ else if (attr == R.styleable.MaterialButtonProcess_icon) {
                setVectorIcon(a.getResourceId(attr, 0));
            } else if (attr == R.styleable.MaterialButtonProcess_textColor) {
                //setFont(a.getString(attr));
                setTextColor(a.getColor(attr, 0));
            } else if (attr == R.styleable.MaterialButtonProcess_iconColor) {
                setColorIcon(/*String.valueOf(a.getColorStateList(attr))*/a.getColor(attr,0));
            } else if (attr == R.styleable.MaterialButtonProcess_progressColor) {
                setProgressColor(a.getColor(attr, 0));
            }
        }
        a.recycle();
    }

    public void setView(boolean emboss) {
        View view;
        removeAllViews();
        if (emboss) {
            view = inflate(getContext(), R.layout.emboss_process_button, null);
        } else {
            view = inflate(getContext(), R.layout.material_process_button, null);
        }
        addView(view);
        //setOnClickListener((OnClickListener) this);
        setType(view);
    }

    /*public MaterialButtonProcess(Context context) {
        super(context);
        setType();
    }*/


    /*public MaterialButtonProcess(View view) {
        setType(view);
    }*/

    private void setType(View view) {
        button_root = view.findViewById(R.id.button_root);
        button_click_parent = view.findViewById(R.id.button_click_parent);
        button_icon = view.findViewById(R.id.button_icon);
        button_progress = view.findViewById(R.id.button_progress);
        button_text = view.findViewById(R.id.button_text);
    }

    public RelativeLayout getButton() {
        return button_click_parent;
    }

    public void setText(String text) {
        button_text.setText(text);
    }

    public void setFont(String fontName) {
        button_text.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName));
    }

    public void setBackgroundColor(ColorStateList color) {
        //button_style_parent.setCardBackgroundColor(/*Color.parseColor(color)*/color);
    }

    public void setVectorIcon(int iconId) {
        button_icon.setImageResource(iconId);
    }

    public void setMBColor(int color) {
        button_root.setRadius(dpToPixel(10));
        button_root.setCardElevation(dpToPixel(10));
        button_root.setCardBackgroundColor(color);
    }

    public void setBackgroundroot(Drawable resource) {
        button_root.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
        //button_root.setRadius(dpToPixelF(1.0f));
        button_root.setCardElevation(dpToPixelF(0.0f));
        button_root.setBackground(resource);
    }

    public void setRadiusPixel(int radius) {
        //button_style_parent.setRadius(dpToPixel(radius));
    }

    private int dpToPixel(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private float dpToPixelF(float dp) {
        return (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void showToast() {
        Toast.makeText(getContext(),
                "Clicked",
                Toast.LENGTH_LONG).show();


    }

    public void start() {
        currentText = button_text.getText().toString();
        button_text.setText("Proccessing");
        button_progress.setVisibility(View.VISIBLE);
    }

    public void stop() {
        button_progress.setVisibility(View.GONE);
        button_text.setText(currentText);
    }
    /*public void process(boolean isVisible) {
        if (isVisible) {
            button_progress.setVisibility(View.VISIBLE);
        } else {
            button_progress.setVisibility(View.GONE);
        }
    }*/

    public void setProgressColor(int color) {
        button_progress.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);

    }

    public void setIconColor(String color) {
        button_icon.setColorFilter(Color.parseColor(color)/*, android.graphics.PorterDuff.Mode.SRC_IN*/);
    }

    public void setColorIcon(int color) {
        //button_icon.setColorFilter(colorStateList);
        button_icon.setColorFilter(color);
    }

    public void setTextColor(int color){
        button_text.setTextColor(color);
    }

    /*@Override
    public void onStartProgress() {
        setProgressVisibility(true);
    }

    @Override
    public void onStopProgress() {
        setProgressVisibility(false);
    }*/

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_style_parent:
                showToast();
                break;
        }
    }*/

    /*@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }*/
}
