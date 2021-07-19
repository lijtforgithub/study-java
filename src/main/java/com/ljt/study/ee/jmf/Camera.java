package com.ljt.study.ee.jmf;

import javax.media.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * @author LiJingTang
 * @date 2020-01-04 15:16
 */
public class Camera extends JFrame {

    public static void main(String[] args) {
        Camera camera = new Camera();
        camera.initCapture();
        // 设置窗体属性
        camera.setTitle("摄像头");
        camera.setBounds(500, 100, 800, 500);
        camera.setVisible(true);
        camera.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private static final long serialVersionUID = 8160948676188570043L;

    /**
     * 初始化启动摄像头
     */
    public void initCapture() {
        try {
            // 获取所有音频、视频设备
            Vector<CaptureDeviceInfo> deviceList = CaptureDeviceManager.getDeviceList(null);

            // 获取视频设备，视频设备以vfw打头(vfw:Microsoft WDM Image Capture (Win32):0)
            CaptureDeviceInfo cameraDevice = null;
            for (CaptureDeviceInfo cameraDeviceTmp : deviceList) {
                if (cameraDeviceTmp.getName().startsWith("vfw")) {
                    cameraDevice = cameraDeviceTmp;
                    break;
                }
            }
            if (cameraDevice == null) {
                System.out.println("找不到摄像头设备");
            }

            // 创建视频播放器
            MediaLocator ml = cameraDevice.getLocator();
            Player player = Manager.createRealizedPlayer(ml);

            if (player == null) {
                System.out.println("创建摄像头播放器失败");
            }

            // 播放视频
            player.start();

            // 将播放器加入窗体
            Component comp = null;
            if ((comp = player.getVisualComponent()) != null) {
                add(comp, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
