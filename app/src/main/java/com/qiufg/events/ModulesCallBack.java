package com.qiufg.events;

/**
 * 
 * ModulesCallBack
 * 
 * @author 丘凤光
 * @description 模块切换的回调
 * @date 2015-3-5
 * @modifier victor qiu
 * 
 */
public interface ModulesCallBack {

	/**
	 * 切换不同模块的Fragment
	 * 
	 * @param btnID
	 *            界面Fragment id
	 * @param args
	 *            传递的参数
	 */
	void toggleActiveFragment(int btnID, String... args);
}
