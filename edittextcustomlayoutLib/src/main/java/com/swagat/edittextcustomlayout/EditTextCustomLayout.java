package com.swagat.edittextcustomlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class EditTextCustomLayout extends RelativeLayout {

    LayoutInflater mInflater;

    TextView tvHelperText;
    TextView tvHintTextView;

    RelativeLayout rlContainer;

    TypedArray att;
    String hintText;

    int hitLeftMargin;
    int userEditTextMerginLeft;
    int hintTextSize;

    int errorColor;
    int hintColor;
    int borderColor;
    int borderColorOnFocus;
    int hintbackgroundcolor;

    int borderWidth;
    int borderWidthOnFocus;

    GradientDrawable layoutBorder,layoutBorderOnFocus,layoutBorderOnError;


    boolean hasError=false;

    EditText userEditText;


    public EditTextCustomLayout(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public EditTextCustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        att = context.getTheme().obtainStyledAttributes(attrs, R.styleable.etParms, 0, 0);
        initView();
    }

    public EditTextCustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        getCustomAttributes();
        setupEditTextLayout();

        setView();
//        View v = mInflater.inflate(R.layout.custom_edittext_parent_layout, this, true);
//
//        tvHintTextView = v.findViewById(R.id.tvHintTextView);
//        tvHelperText = v.findViewById(R.id.tvHelperText);
//        rlContainer = v.findViewById(R.id.rlContainer);





        tvHintTextView.setText(hintText);
        tvHintTextView.setTextSize(hintTextSize);
        setTvHintViewBackgroundToTransparent();
        tvHintTextView.setY(rlContainer.getHeight()/2-10);

    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if(child instanceof EditText){

            final EditText editText=(EditText) child;

            userEditText=editText;

            if(!userEditText.getText().toString().equals("")){
                tvHintTextView.animate().translationY(-rlContainer.getHeight() / 2-18);
                tvHintTextView.animate().translationX(hitLeftMargin);
                tvHintTextView.animate().scaleX(0.8f);
                tvHintTextView.animate().scaleY(0.8f);
            }

            LayoutParams etParm = (LayoutParams) editText.getLayoutParams();
            userEditTextMerginLeft=etParm.leftMargin;


            setMarginOrPaddintToTextHintView(etParm,editText.getPaddingLeft(),editText.getPaddingBottom());
            setTextOrHintSize(editText.getTextSize());

            editText.setBackgroundDrawable(null);
            editText.clearFocus();


            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if(hasFocus){

                        if(!hasError) {
                            userEditText.setTextColor(hintColor);
                            tvHintTextView.setTextColor(borderColorOnFocus);
                            rlContainer.setBackgroundDrawable(layoutBorderOnFocus);
                            tvHintTextView.animate().translationY(-rlContainer.getHeight() / 2-18);
                            tvHintTextView.animate().translationX(hitLeftMargin);
                            tvHintTextView.animate().scaleX(0.8f);
                            tvHintTextView.animate().scaleY(0.8f);
                            setTvHintViewBackground();
                        }else {
                            tvHintTextView.animate().translationY(-rlContainer.getHeight() / 2-18);
                            tvHintTextView.animate().translationX(hitLeftMargin);
                            tvHintTextView.animate().scaleX(0.8f);
                            tvHintTextView.animate().scaleY(0.8f);
                            setTvHintViewBackground();
                        }
                    }else {

                        if (!hasError) {
                            userEditText.setTextColor(hintColor);
                            tvHintTextView.setTextColor(borderColor);

                            if (editText.getText().toString().equals("")) {

                                rlContainer.setBackgroundDrawable(layoutBorder);
                                tvHintTextView.animate().translationY(-20);
                                tvHintTextView.animate().translationX(0);
                                tvHintTextView.animate().scaleX(1);
                                tvHintTextView.animate().scaleY(1);
                                // setTvHintViewBackgroundToTransparent();

                            }else {
                                tvHintTextView.animate().translationY(-rlContainer.getHeight() / 2-18);
                                tvHintTextView.animate().translationX(hitLeftMargin);
                                tvHintTextView.animate().scaleX(0.8f);
                                tvHintTextView.animate().scaleY(0.8f);
                                rlContainer.setBackgroundDrawable(layoutBorder);

                            }
                        }
                        else {

                            if(editText.getText().toString().equals("")) {

                                tvHintTextView.animate().translationY(-20);
                                tvHintTextView.animate().translationX(0);
                                tvHintTextView.animate().scaleX(1);
                                tvHintTextView.animate().scaleY(1);
                                //  setTvHintViewBackgroundToTransparent();
                            }
                        }
                    }
                }
            });

        }

    }

    private void setMarginOrPaddintToTextHintView(LayoutParams etparm, int paddingLeft, int paddingBottom) {

        LayoutParams tvP= (LayoutParams) tvHintTextView.getLayoutParams();
        tvP.addRule(CENTER_VERTICAL, RelativeLayout.TRUE);

        if(etparm.leftMargin!=0) {

            if(paddingLeft==0) {
                tvP.setMargins(etparm.leftMargin, etparm.topMargin,etparm.rightMargin,paddingBottom);
            }else {
                tvP.setMargins(etparm.leftMargin+paddingLeft, etparm.topMargin,etparm.rightMargin,paddingBottom);
            }
        }else {
            if(paddingLeft!=0){
                tvP.setMargins(paddingLeft, etparm.topMargin,etparm.rightMargin,paddingBottom);
            }
        }

        tvHintTextView.setLayoutParams(tvP);

    }

    private void setTvHintViewBackgroundToTransparent(){
        tvHintTextView.setBackgroundDrawable(null);
    }
    private void setTvHintViewBackground(){
        tvHintTextView.setBackgroundColor(hintbackgroundcolor);
    }
    private void setTextOrHintSize(float etParm){

        tvHintTextView.setTextSize(pxToDp((int) etParm));
    }
    public int pxToDp(int px) {

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void setError(String errMsg){

        userEditText.setTextColor(errorColor);
        rlContainer.setBackgroundDrawable(layoutBorderOnError);
        tvHintTextView.setTextColor(errorColor);
        tvHelperText.setTextColor(errorColor);
        tvHelperText.setVisibility(VISIBLE);
        tvHelperText.setText(errMsg);
        hasError=true;
    }
    public void setErrorSpannable(SpannableString txt){
        userEditText.setTextColor(errorColor);
        rlContainer.setBackgroundDrawable(layoutBorderOnError);
        tvHintTextView.setTextColor(errorColor);
        tvHelperText.setTextColor(errorColor);
        tvHelperText.setVisibility(VISIBLE);
        tvHelperText.setText(txt);
        hasError=true;
    }

    public void setErrorEnable(boolean hasErrorEnable){

        if(!hasErrorEnable){

            hasError=false;
            userEditText.setTextColor(hintColor);
            if(userEditText.isFocused()){
                rlContainer.setBackgroundDrawable(layoutBorderOnFocus);
                tvHelperText.setVisibility(INVISIBLE);
                tvHintTextView.setTextColor(borderColorOnFocus);

            }else{
                rlContainer.setBackgroundDrawable(layoutBorder);
                tvHintTextView.setTextColor(hintColor);
                tvHelperText.setVisibility(INVISIBLE);
                hasError=false;
                tvHelperText.setVisibility(INVISIBLE);
            }



        }
    }

    public void setHintText(SpannableString text){

        tvHintTextView.setText(text);
    }

    public void EditTexthasText(){

        tvHintTextView.animate().translationY(-rlContainer.getHeight() / 2-18);
        tvHintTextView.animate().translationX(hitLeftMargin);
        tvHintTextView.animate().scaleX(0.8f);
        tvHintTextView.animate().scaleY(0.8f);
    }


    private void setupEditTextLayout(){

        layoutBorder = (GradientDrawable) getResources().getDrawable(R.drawable.edittext_parent_layout_background);
        layoutBorder.setStroke(borderWidth,borderColor);

        layoutBorderOnFocus = (GradientDrawable) getResources().getDrawable(R.drawable.edittext_parent_layout_background_on_focus);
        layoutBorderOnFocus.setStroke(borderWidthOnFocus,borderColorOnFocus);

        layoutBorderOnError = (GradientDrawable) getResources().getDrawable(R.drawable.edittext_parent_layout_background_on_error);
        layoutBorderOnError.setStroke(borderWidth,errorColor);

    }

    private void getCustomAttributes(){

        hintText= att.getString(R.styleable.etParms_hinttext);
        hitLeftMargin=att.getDimensionPixelSize(R.styleable.etParms_hintmarginleft,0);
        hintTextSize = att.getDimensionPixelSize(R.styleable.etParms_hintTextSize,12);

        hintColor = att.getColor(R.styleable.etParms_hintcolor,Color.WHITE);
        borderColor = att.getColor(R.styleable.etParms_bordercolor,Color.WHITE);
        errorColor = att.getColor(R.styleable.etParms_errorcolor,Color.RED);
        hintbackgroundcolor = att.getColor(R.styleable.etParms_hintbackgroundcolor,Color.WHITE);

        borderWidth = att.getDimensionPixelSize(R.styleable.etParms_borderwidth,2);
        borderWidthOnFocus = att.getDimensionPixelSize(R.styleable.etParms_borderwidthonfocus,4);

        borderColorOnFocus = att.getColor(R.styleable.etParms_bordercoloronfocus,Color.WHITE);
    }

    @SuppressLint("ResourceType")
    private void setView(){

        RelativeLayout.LayoutParams rlMainContainerParms=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout rlMainContainer=new RelativeLayout(getContext());
        rlMainContainer.setBackgroundColor(Color.TRANSPARENT);
        rlMainContainer.setLayoutParams(rlMainContainerParms);

        ////////////////////////////////

        RelativeLayout.LayoutParams tvHelpHintTextParms=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvHelpHintTextParms.addRule(ALIGN_PARENT_BOTTOM);
        tvHelpHintTextParms.setMargins(15,2,0,0);
        tvHelperText=new TextView(getContext());
        tvHelperText.setId(12);
        tvHelperText.setVisibility(INVISIBLE);
        tvHelperText.setTextColor(errorColor);
        tvHelperText.setLayoutParams(tvHelpHintTextParms);

        ////////////////////////////


        RelativeLayout.LayoutParams rlContainerParms=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlContainerParms.setMargins(0,18,0,0);
        rlContainerParms.addRule(ABOVE,tvHelperText.getId());
        rlContainer=new RelativeLayout(getContext());
        rlContainer.setLayoutParams(rlContainerParms);
        rlContainer.setBackgroundDrawable(layoutBorder);

        ///////////////////

        RelativeLayout.LayoutParams tvHintParms=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvHintTextView=new TextView(getContext());
        tvHintTextView.setLayoutParams(tvHintParms);
        tvHintTextView.setVisibility(VISIBLE);
        tvHintTextView.setPadding(15,0,15,0);
        tvHintTextView.setBackgroundColor(hintbackgroundcolor);
        tvHintTextView.setText(hintText);
        tvHintTextView.setTextSize(hintTextSize);
        tvHintTextView.setTextColor(hintColor);


        //////////////////////////////////////


        rlMainContainer.addView(rlContainer);
        rlMainContainer.addView(tvHintTextView);
        rlMainContainer.addView(tvHelperText);

        addView(rlMainContainer);

    }
}
