package com.tilldawn.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tilldawn.Main;
import javax.swing.*;
import java.io.File;
import com.tilldawn.util.FileChooserInterface;
import com.tilldawn.View.GameView;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        FileChooserInterface fileChooser = new FileChooserInterface() {
            @Override
            public String chooseFile() {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selected = chooser.getSelectedFile();
                    return selected.getAbsolutePath();
                }
                return null;
            }
        };
        createApplication(fileChooser);
    }

//    private static Lwjgl3Application createApplication(FileChooserInterface fileChooser) {
//        // فرض می‌کنیم Main یک کانستراکتور جدید می‌پذیرد:
//        // public Main(FileChooserInterface fileChooser) { … }
//        return new Lwjgl3Application(new Main(fileChooser), getDefaultConfiguration());
//    }

    private static Lwjgl3Application createApplication(FileChooserInterface fileChooser) {
        Main main = new Main(fileChooser);
        Lwjgl3Application app = new Lwjgl3Application(main, getDefaultConfiguration());

        // ایجاد یک JFrame کوچک نامرئی برای دریافت درگ و دراپ
        JFrame dragFrame = new JFrame();
        dragFrame.setUndecorated(true);
        dragFrame.setSize(400, 400); // اندازه دلخواه
        dragFrame.setLocationRelativeTo(null);
        dragFrame.setAlwaysOnTop(true);
        dragFrame.setOpacity(0.01f); // تقریبا نامرئی
        dragFrame.setVisible(true);

        // اضافه کردن قابلیت درگ و دراپ
        new java.awt.dnd.DropTarget(dragFrame, new java.awt.dnd.DropTargetAdapter() {
            @Override
            public void drop(java.awt.dnd.DropTargetDropEvent event) {
                try {
                    event.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>)
                        event.getTransferable().getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);

                    for (File file : droppedFiles) {
                        if (file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".jpg")) {
                            main.onAvatarDropped(file.getAbsolutePath());
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return app;
    }



    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("untildawn");
        configuration.setDecorated(true);
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

        configuration.setWindowedMode(1920, 1080);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
