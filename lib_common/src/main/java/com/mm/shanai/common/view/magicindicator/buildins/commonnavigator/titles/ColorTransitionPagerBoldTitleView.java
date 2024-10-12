package com.mm.shanai.common.view.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.util.AttributeSet;

import com.mm.shanai.common.view.magicindicator.buildins.ArgbEvaluatorHolder;


/**
 * 两种颜色过渡的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class ColorTransitionPagerBoldTitleView extends SimplePagerBoldTitleView {

    public ColorTransitionPagerBoldTitleView(Context context) {
        super(context);
    }

    public ColorTransitionPagerBoldTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
        setTextColor(color);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
        setTextColor(color);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        getPaint().setFakeBoldText(true);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        getPaint().setFakeBoldText(false);
    }
}
