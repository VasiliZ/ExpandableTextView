package com.github.vasiliz.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableTextView extends LinearLayout {

    public static final float MULT = 1.25f;
    public static final float SIZE_TEXT_16F = 16f;
    public static final float SIZE_TEXT_14F = 14f;
    public static final float DEFAULT_SPACING = 1;
    private final int VISIBLE_TEXT_LINES = 5;
    private TextView mTextView;
    private TextView mShowMore;
    private String TAG = "log";
    private CharSequence mFullText;
    private int mLineText;
    private boolean isExpand;
    private String mTextForExpand;
    private int mColorForExpandText;
    private float mTextSizeForExpandText;
    private float mMainTextSize;
    private float mLineSpacing;

    public ExpandableTextView(final Context context) {
        super(context);

    }

    public ExpandableTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpandableTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    public void setContent(final String pText) {
        measure(getWidth(), getHeight());
        mTextView.setText(pText);
    }

    private void init(@Nullable final AttributeSet pAttributeSet) {

        TypedArray typedArray = getContext().obtainStyledAttributes(pAttributeSet, R.styleable.ExpandableTextView);
        mTextForExpand = typedArray.getString(R.styleable.ExpandableTextView_text_for_expand);
        mColorForExpandText = typedArray.getColor(R.styleable.ExpandableTextView_color_expand_text, Color.BLACK);
        mTextSizeForExpandText = typedArray.getFloat(R.styleable.ExpandableTextView_text_size_expand_text, SIZE_TEXT_16F);
        mMainTextSize = typedArray.getFloat(R.styleable.ExpandableTextView_main_text_size, SIZE_TEXT_14F);
        mLineSpacing = typedArray.getFloat(R.styleable.ExpandableTextView_line_spacing, DEFAULT_SPACING);
        typedArray.recycle();
        initViews();

    }

    private void initViews() {
        setOrientation(VERTICAL);
        mTextView = new TextView(getContext());
        final LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTextView.setLayoutParams(layoutParams);
        mShowMore = new TextView(getContext());
        final LayoutParams layoutParamsShowMore = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mShowMore.setLayoutParams(layoutParamsShowMore);

        mTextView.setLineSpacing(1, mLineSpacing);
        mTextView.setTextSize(mMainTextSize);

        mShowMore.setText(mTextForExpand);
        mShowMore.setTextSize(mTextSizeForExpandText);
        mShowMore.setTextColor(mColorForExpandText);
        mShowMore.setVisibility(GONE);

        addView(mTextView);
        addView(mShowMore);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLineText = mTextView.getLineCount();
        if (mTextView.getLineCount() != 0 && !isExpand) {
            final ViewTreeObserver viewTreeObserver = mTextView.getViewTreeObserver();

            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    if (viewTreeObserver.isAlive()) {
                        mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        trimLongText(mLineText);
                        isExpand = true;
                    }
                }

                private void trimLongText(final int fullTextCountLine) {
                    if (fullTextCountLine > VISIBLE_TEXT_LINES) {
                        mTextView.setMaxLines(VISIBLE_TEXT_LINES);
                        mFullText = mTextView.getText();
                        final CharSequence tempForFullText = mFullText;
                        tempForFullText.subSequence(0, mTextView.getLayout().getLineVisibleEnd(4));
                        mShowMore.setVisibility(VISIBLE);
                        mShowMore.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(final View v) {
                                mTextView.setMaxLines(Integer.MAX_VALUE);
                                mTextView.setText(mFullText);
                                mShowMore.setVisibility(GONE);
                            }
                        });
                    }
                }
            });
        } else {
            mTextView.setText(mFullText);
        }

    }

}



