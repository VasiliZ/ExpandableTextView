package com.github.vasiliz.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

    public static final float SIZE_TEXT_16F = 16f;
    public static final float SIZE_TEXT_14F = 14f;
    public static final float DEFAULT_SPACING = 1;
    private final int LENGTH_TEXT_FOR_EXPAND = 300;
    private TextView mTextView;
    private TextView mShowMore;
    private CharSequence mFullText;
    private boolean isExpand;
    private String mTextForExpand;
    private int mColorForExpandText;
    private float mTextSizeForExpandText;
    private float mMainTextSize;
    private float mLineSpacing;
    private OnClickListener mOnClickListener;

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

    public void setContent(final CharSequence pText) {
        mTextView.setTag(pText);
        mFullText = pText;
        trimLongText();
    }

    private void init(@Nullable final AttributeSet pAttributeSet) {

        final TypedArray typedArray = getContext().obtainStyledAttributes(pAttributeSet, R.styleable.ExpandableTextView);
        mTextForExpand = typedArray.getString(R.styleable.ExpandableTextView_text_for_expand);
        mColorForExpandText = typedArray.getColor(R.styleable.ExpandableTextView_color_expand_text, Color.BLACK);
        mTextSizeForExpandText = typedArray.getFloat(R.styleable.ExpandableTextView_text_size_expand_text, SIZE_TEXT_16F);
        mMainTextSize = typedArray.getFloat(R.styleable.ExpandableTextView_main_text_size, SIZE_TEXT_14F);
        mLineSpacing = typedArray.getFloat(R.styleable.ExpandableTextView_line_spacing, DEFAULT_SPACING);
        typedArray.recycle();
        initViews();
        isExpand = false;
        mTextView.setOnClickListener(this);

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
        mTextView.setAutoLinkMask(Linkify.ALL);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        mShowMore.setText(mTextForExpand);
        mShowMore.setTextSize(mTextSizeForExpandText);
        mShowMore.setTextColor(mColorForExpandText);
        mShowMore.setVisibility(GONE);

        addView(mTextView);
        addView(mShowMore);
        invalidate();
    }

    private void trimLongText() {
        if (mFullText.length() > LENGTH_TEXT_FOR_EXPAND) {
            final CharSequence tempForFullText = mFullText.subSequence(0, LENGTH_TEXT_FOR_EXPAND);
            mTextView.setText(tempForFullText);
            mShowMore.setVisibility(VISIBLE);
            mShowMore.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {
                    mTextView.setMaxLines(Integer.MAX_VALUE);
                    if (mTextView.getTag().equals(mFullText)) {
                        mTextView.setText(mTextView.getTag().toString());
                        mShowMore.setVisibility(GONE);
                        isExpand = true;
                    }
                }
            });
        } else {
            mShowMore.setVisibility(GONE);
            mTextView.setText(mTextView.getTag().toString());
        }
    }

    @Override
    public void onClick(final View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }

    }

    public void setOnClickListener(final OnClickListener pOnClickListener) {
        mOnClickListener = pOnClickListener;
    }
}



