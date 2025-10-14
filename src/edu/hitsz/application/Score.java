package edu.hitsz.application;

import edu.hitsz.dao.FileScoreDaoImpl;
import edu.hitsz.dao.ScoreDao;
import edu.hitsz.dao.ScoreRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Score extends JPanel {
    private JPanel Panel;
    private JPanel flowLayout;
    private JLabel rank;
    private JLabel difficultyLabel;
    private JLabel difficulty;
    private JScrollPane scrollPane;
    private JTable scoreTable;
    private JButton deleteButton;

    private final ScoreDao scoreDao;
    private DefaultTableModel tableModel;

    public Score(String difficultyStr) {
        this.scoreDao = new FileScoreDaoImpl("ranklist_db/scores.dat");
        this.difficulty.setText(difficultyStr);

        // 初始化表格模型
        String[] columnNames = { "排名", "玩家", "得分", "记录时间" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };
        scoreTable.setModel(tableModel);
        scoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选模式

        // 加载并显示数据
        loadScoreData();

        // 删除按钮事件监听
        deleteButton.addActionListener(e -> {
            int selectedRow = scoreTable.getSelectedRow();
            if (selectedRow != -1) {
                int choice = JOptionPane.showConfirmDialog(this, "确定要删除这条记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // 从数据源删除
                    List<ScoreRecord> scores = scoreDao.getAllScores();
                    Collections.sort(scores);
                    scores.remove(selectedRow);
                    saveScores(scores);

                    // 刷新表格
                    loadScoreData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先选择要删除的记录！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(Panel);
    }

    private void loadScoreData() {
        List<ScoreRecord> scores = scoreDao.getAllScores();
        Collections.sort(scores); // 按分数降序排序

        // 清空现有数据
        tableModel.setRowCount(0);

        // 填充新数据
        for (int i = 0; i < scores.size(); i++) {
            ScoreRecord r = scores.get(i);
            tableModel.addRow(new Object[] {
                    i + 1,
                    r.getPlayerName(),
                    r.getScore(),
                    r.getFormattedTime()
            });
        }
    }

    private void saveScores(List<ScoreRecord> scores) {
        // 由于 ScoreDao 没有提供直接覆盖保存的方法，这里通过清空再逐一添加的方式模拟
        // 注意：这是一种效率较低的实现，更好的方式是在 DAO 中提供一个 `saveAll` 方法
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(
                new java.io.FileOutputStream("ranklist_db/scores.dat"))) {
            oos.writeObject(scores);
        } catch (java.io.IOException e) {
            System.err.println("Error saving score file: " + e.getMessage());
        }
    }
}