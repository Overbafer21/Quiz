import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuizApp extends JFrame {

    private final String[] questions = {
            "Столица Франции?",
            "Какая планета известна как Красная планета?",
            "Кто написал «Ромео и Джульетту»?",
            "При какой температуре кипит вода (при 1 атм)?",
            "Какое самое крупное млекопитающее?"
    };

    private final String[][] options = {
            {"Берлин", "Мадрид", "Париж", "Лондон"},
            {"Земля", "Марс", "Юпитер", "Венера"},
            {"Уильям Шекспир", "Чарльз Диккенс", "Марк Твен", "Лев Толстой"},
            {"100°C", "90°C", "120°C", "80°C"},
            {"Слон", "Синий кит", "Жираф", "Бегемот"}
    };

    // Индексы правильных ответов
    private final int[] answers = {2, 1, 0, 0, 1};

    private int currentQuestion = 0;
    private int score = 0;

    private final JLabel lblTitle;
    private final JLabel lblDescription;
    private final JLabel lblQuestion;
    private final JLabel lblProgress;
    private final JRadioButton[] radioOptions;
    private final ButtonGroup optionsGroup;
    private final JButton btnNext;

    public QuizApp() {
        setTitle("Мини-викторина");
        setSize(640, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(new Color(245, 248, 255));

        JPanel topPanel = new JPanel(new BorderLayout(6, 6));
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(16, 20, 0, 20));

        lblTitle = new JLabel("Проверьте свои знания");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(34, 60, 130));

        lblDescription = new JLabel("Небольшой тест из 5 вопросов по общим фактам.");
        lblDescription.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescription.setForeground(new Color(80, 80, 80));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1));
        titleBlock.setOpaque(false);
        titleBlock.add(lblTitle);
        titleBlock.add(lblDescription);

        lblProgress = new JLabel();
        lblProgress.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblProgress.setForeground(new Color(65, 65, 65));

        topPanel.add(titleBlock, BorderLayout.WEST);
        topPanel.add(lblProgress, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(12, 12));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(8, 20, 0, 20));

        lblQuestion = new JLabel();
        lblQuestion.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblQuestion.setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(new EmptyBorder(4, 8, 4, 8));

        radioOptions = new JRadioButton[4];
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            radioOptions[i] = new JRadioButton();
            radioOptions[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            radioOptions[i].setOpaque(false);
            optionsGroup.add(radioOptions[i]);
            optionsPanel.add(radioOptions[i]);
        }

        centerPanel.add(lblQuestion, BorderLayout.NORTH);
        centerPanel.add(optionsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(0, 20, 16, 20));

        btnNext = new JButton("Далее");
        btnNext.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnNext.setBackground(new Color(57, 92, 192));
        btnNext.setForeground(Color.WHITE);
        btnNext.setFocusPainted(false);
        btnNext.setPreferredSize(new Dimension(130, 36));

        btnNext.addActionListener(e -> {
            if (!isAnswerSelected()) {
                JOptionPane.showMessageDialog(this, "Пожалуйста, выберите вариант ответа.", "Внимание", JOptionPane.WARNING_MESSAGE);
                return;
            }

            checkAnswer();
            currentQuestion++;

            if (currentQuestion < questions.length) {
                loadQuestion(currentQuestion);
            } else {
                showResult();
            }
        });

        bottomPanel.add(btnNext);
        add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion(currentQuestion);
    }

    private void loadQuestion(int index) {
        lblQuestion.setText("Вопрос " + (index + 1) + ": " + questions[index]);
        lblProgress.setText("Прогресс: " + (index + 1) + "/" + questions.length);

        optionsGroup.clearSelection();
        for (int i = 0; i < 4; i++) {
            radioOptions[i].setText(options[index][i]);
        }

        if (index == questions.length - 1) {
            btnNext.setText("Завершить");
        } else {
            btnNext.setText("Далее");
        }
    }

    private boolean isAnswerSelected() {
        for (JRadioButton radio : radioOptions) {
            if (radio.isSelected()) {
                return true;
            }
        }
        return false;
    }

    private void checkAnswer() {
        for (int i = 0; i < radioOptions.length; i++) {
            if (radioOptions[i].isSelected() && i == answers[currentQuestion]) {
                score++;
                break;
            }
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(
                this,
                "Вы завершили викторину!\nВаш результат: " + score + " из " + questions.length,
                "Результат",
                JOptionPane.INFORMATION_MESSAGE
        );

        int restart = JOptionPane.showConfirmDialog(
                this,
                "Хотите пройти викторину ещё раз?",
                "Новая попытка",
                JOptionPane.YES_NO_OPTION
        );

        if (restart == JOptionPane.YES_OPTION) {
            currentQuestion = 0;
            score = 0;
            loadQuestion(currentQuestion);
        } else {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
    }
}
