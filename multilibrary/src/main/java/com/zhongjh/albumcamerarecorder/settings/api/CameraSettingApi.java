package com.zhongjh.albumcamerarecorder.settings.api;

import androidx.annotation.NonNull;

import com.zhongjh.albumcamerarecorder.settings.CameraSetting;
import com.zhongjh.albumcamerarecorder.settings.GlobalSetting;
import com.zhongjh.albumcamerarecorder.settings.MultiMediaSetting;

import java.util.Set;

import gaode.zhongjh.com.common.enums.MimeType;

/**
 * 有关拍摄界面的动态设置
 * Created by zhongjh on 2019/3/20.
 */
public interface CameraSettingApi {

    /**
     * 支持的类型：图片，视频
     * 这个优先于 {@link MultiMediaSetting#choose}
     *
     * @param mimeTypes 类型
     * @return this
     */
    CameraSetting mimeTypeSet(@NonNull Set<MimeType> mimeTypes);

    /**
     * 最长录制时间,默认10秒
     *
     * @param duration 最长录制时间,单位为秒
     * @return {@link GlobalSetting} for fluent API.
     */
    CameraSetting duration(int duration);

    /**
     * 最短录制时间限制，单位为毫秒，即是如果长按在1500毫秒内，都暂时不开启录制
     *
     * @param minDuration 最短录制时间限制，单位为毫秒
     * @return {@link GlobalSetting} for fluent API.
     */
    CameraSetting minDuration(int minDuration);

    /**
     * 更换 切换前置/后置摄像头图标资源
     *
     * @param imageSwitch 切换前置/后置摄像头图标资源
     * @return {@link GlobalSetting} for fluent API.
     */
    CameraSetting imageSwitch(int imageSwitch);

    /**
     * 更换 闪光灯开启状态图标
     *
     * @param imageFlashOn 闪光灯开启状态图标
     * @return {@link GlobalSetting} for fluent API.
     */
    CameraSetting imageFlashOn(int imageFlashOn);

    /**
     * 更换 闪光灯关闭状态图标
     *
     * @param imageFlashOff 闪光灯关闭状态图标
     * @return {@link GlobalSetting} for fluent API.
     */
    CameraSetting imageFlashOff(int imageFlashOff);

    /**
     * 更换 闪光灯自动状态图标
     *
     * @param imageFlashAuto 闪光灯自动状态图标
     * @return {@link GlobalSetting} for fluent API.
     */
    CameraSetting imageFlashAuto(int imageFlashAuto);

}
