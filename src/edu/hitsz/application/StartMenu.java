package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel {

    private final JFrame mainFrame;
    private boolean soundEnabled = true;
    private JPanel panel1;
    private JButton easy;
    private JButton normal;
    private JButton hard;
    private JLabel title;
    private JComboBox<String> soundComboBox;
    private JLabel soundLabel;

    public StartMenu(JFrame frame) {
        this.mainFrame = frame;
        
        this.add(panel1);

        // 为按钮添加事件监听器
        easy.addActionListener(e -> startGame("Easy"));
        normal.addActionListener(e -> startGame("Normal"));
        hard.addActionListener(e -> startGame("Hard"));

        // 为下拉框添加事件监听器
        soundComboBox.addActionListener(e -> {
            String selected = (String) soundComboBox.getSelectedItem();
            soundEnabled = "开".equals(selected);
            System.out.println("Sound " + (soundEnabled ? "Enabled" : "Disabled"));
        });
    }

    private void startGame(String difficulty) {
        mainFrame.getContentPane().removeAll();
        // 注意：您可能需要将 soundEnabled 状态传递给 Game 对象
        Game game = new Game(difficulty, soundEnabled);
        mainFrame.add(game);
        mainFrame.revalidate();
        mainFrame.repaint();
        game.action();
    }
}