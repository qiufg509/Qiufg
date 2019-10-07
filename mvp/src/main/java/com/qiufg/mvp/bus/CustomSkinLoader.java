package com.qiufg.mvp.bus;

import skin.support.load.SkinSDCardLoader;

public class CustomSkinLoader extends SkinSDCardLoader {
    public static final int SKIN_LOADER_STRATEGY_SDCARD = Integer.MAX_VALUE;

    @Override
    protected String getSkinPath(Context context, String skinName) {
        return new File(SkinFileUtils.getSkinDir(context), skinName).getAbsolutePath();
    }

    @Override
    public int getType() {
        return SKIN_LOADER_STRATEGY_SDCARD;
    }
}
