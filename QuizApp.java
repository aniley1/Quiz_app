/*code by Arnav Kumar
          BSC CS , MITWPU , KOTHRUD
		  TASK 3:  QUIZ APP
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp extends JFrame {
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton submitButton;
    private JLabel timerLabel;
    private Timer timer;
    private int remainingTime = 15; // Time in seconds for each question

    private String[] questions = {
        "What is the capital of France?",
        "Which planet is known as the Red Planet?",
        "What is the largest mammal?",
        "What is the chemical symbol for gold?",
		"which animal have its heart in its head?"
    };

    private String[][] options = {
        {"Paris", "Berlin", "London", "Madrid"},
        {"Mars", "Venus", "Jupiter", "Saturn"},
        {"Blue whale", "African elephant", "Giraffe", "Hippopotamus"},
        {"Go", "Ag", "Au", "Pt"},
		{"Bat","Ant","Bee","Shrimp"}
    };

    private int[] correctAnswers = {0, 0, 0, 2, 3}; // Index of correct options

    public QuizApp() {
        setTitle("Quiz App");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsPanel.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        add(submitButton, BorderLayout.SOUTH);

        timerLabel = new JLabel();
        add(timerLabel, BorderLayout.EAST);

        loadQuestion(currentQuestionIndex);
        startTimer();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    loadQuestion(currentQuestionIndex);
                    resetTimer();
                } else {
                    showResult();
                }
            }
        });
    }

    private void loadQuestion(int index) {
        questionLabel.setText(questions[index]);
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[index][i]);
            optionButtons[i].setSelected(false);
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timerLabel.setText("Time: " + remainingTime + "s");
                if (remainingTime <= 0) {
                    checkAnswer();
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        loadQuestion(currentQuestionIndex);
                        resetTimer();
                    } else {
                        showResult();
                    }
                }
            }
        });
        timer.start();
    }

    private void resetTimer() {
        remainingTime = 15;
        timerLabel.setText("Time: " + remainingTime + "s");
    }

    private void checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected() && i == correctAnswers[currentQuestionIndex]) {
                score++;
            }
        }
    }

    private void showResult() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Quiz Completed!\nYour Score: " + score + "/" + questions.length);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApp().setVisible(true);
            }
        });
    }
}
