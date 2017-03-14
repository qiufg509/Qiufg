package com.qiufg.fragment.base;

import android.support.v4.app.Fragment;

import rx.Subscription;

/**
 * Author qiufg
 * Date 2017/2/19
 */
public class BaseFragment extends Fragment {

    protected Subscription mSubscription;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
