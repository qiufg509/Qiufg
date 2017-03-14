package com.qiufg.fragment.base;

import android.os.Bundle;

/**
 * Description
 * Author qiufg
 * Date 2017/3/14 1:30
 */

public abstract class BasePageFragment extends BaseFragment {

    /**
     * 界面是否初始化
     */
    protected boolean isViewInitiated;
    /**
     * 用户是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 数据是否初始化
     */
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    /**
     * setUserVisibleHint需要说下，这是一个相当生僻的方法，我们可以用这个方法来判断当前UI是否可见
     *
     * @param isVisibleToUser 是否课件
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    /**
     * 获取数据，详见 {@link #prepareFetchData(boolean)} .
     * <p>
     * prepareFetchData()，传true即可强制刷新
     */
    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    /**
     * 就是当前UI可见，并且fragment已经初始化完毕，如果网络数据未加载，那么请求数据，或者需要强制刷新页面，那么也去请求数据
     *
     * @param forceUpdate 是否强制获取数据
     * @return 数据初始化
     */
    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
