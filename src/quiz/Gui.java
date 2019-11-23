package quiz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static quiz.SwingSetup.*;


public class Gui extends JFrame implements ActionListener {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private List<JButton> answerButtons;
    private List<JButton> categoryButtons;

    private JLabel infoText = createLabel("Väntar på att en motståndare ska ansluta...");
    private JLabel questionText = createLabel("questiontext");
    private ServerHandler serverHandler;
    private Question currentQuestion;


    public Gui(ServerHandler serverHandler) {

        this.serverHandler = serverHandler;

        answerButtons = Arrays.asList(createButton(), createButton(), createButton(), createButton());
        categoryButtons = Arrays.asList(createButton(), createButton(), createButton(), createButton());

        //mainPanel
        JPanel infoPanel = createPanel();
        JPanel game = createPanel();
        JPanel category = createPanel();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(infoPanel, "info");
        mainPanel.add(game, "game");
        mainPanel.add(category, "category");

        //loadingPanel
        JPanel loadingPanel = createPanel();
        loadingPanel.setLayout(new GridLayout(2, 1));
        loadingPanel.add(getLoaderLabel());
        loadingPanel.add(infoText);

        //InfoPanel
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(createLogo(), BorderLayout.NORTH);
        infoPanel.add(loadingPanel, BorderLayout.CENTER);

        //gameButtonsPanel
        JPanel gameButtonsPanel = createPanel();
        gameButtonsPanel.setLayout(new GridLayout(2, 2, 10, 10));
        gameButtonsPanel.setBorder(getEmptyBorder());

        answerButtons.forEach(button -> {
            gameButtonsPanel.add(button);
            button.addActionListener(this);
        });

        //gamePanel
        questionText.setForeground(Color.WHITE);
        questionText.setVerticalAlignment(JLabel.CENTER);
        game.setLayout(new BorderLayout());
        game.add(createLogo(), BorderLayout.NORTH);
        game.add(gameButtonsPanel, BorderLayout.SOUTH);
        game.add(questionText, BorderLayout.CENTER);

        //categoryButtonsPanel
        JPanel categoryButtonsPanel = createPanel();
        categoryButtonsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        categoryButtonsPanel.setBorder(getEmptyBorder());


        categoryButtons.forEach(button -> {
            categoryButtonsPanel.add(button);
            button.addActionListener(this);
        });

        //categoryPanel
        JLabel categorytext = createLabel("Välj kategori");
        categorytext.setVerticalAlignment(JLabel.CENTER);
        category.setLayout(new BorderLayout());
        category.add(createLogo(), BorderLayout.NORTH);
        category.add(categorytext, BorderLayout.CENTER);
        category.add(categoryButtonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setVisible(true);
        setSize(new Dimension(400, 600));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            try {
                for (JButton categoryButton : categoryButtons) {
                    if (e.getSource() == categoryButton) {
                        serverHandler.sendCategory(categoryButton.getText());
                    }
                }
                for (JButton answerButton : answerButtons) {
                    if (e.getSource() == answerButton) {
                        isCorrectAnswer(answerButton);
                        changeButtonColour((JButton) e.getSource());
                    }
                }
            } catch (Exception ea) {
                System.out.println(ea.getMessage());
            }
        }


    private void changeButtonColour(JButton button) throws InterruptedException {
        answerButtons.get(0).setBackground(Color.red);
        answerButtons.get(1).setBackground(Color.red);
        answerButtons.get(2).setBackground(Color.red);
        answerButtons.get(3).setBackground(Color.green);
        button.setBorder(new LineBorder(Color.black));
        cardLayout.show(mainPanel, "game");
        repaint();
//        answerButtons.get(0).setBackground(null);
//        answerButtons.get(1).setBackground(null);
//        answerButtons.get(2).setBackground(null);
//        answerButtons.get(3).setBackground(null);
//        button.setBorder(new LineBorder(Color.WHITE));
//        cardLayout.show(mainPanel, "game");
//        repaint();


    }

    public void isCorrectAnswer(JButton b) throws InterruptedException {
        if (b.getText().equals(currentQuestion.getAnswerCorrect())) {
            serverHandler.writeStringToServer("correct");

        } else {
            serverHandler.writeStringToServer("wrong");

        }
    }

    public void setQuestionPanel() {
        Collections.shuffle(answerButtons);
        questionText.setText(currentQuestion.getQuestionText());
        answerButtons.get(0).setText(currentQuestion.getAnswerOne());
        answerButtons.get(1).setText(currentQuestion.getAnswerTwo());
        answerButtons.get(2).setText(currentQuestion.getAnswerThree());
        answerButtons.get(3).setText(currentQuestion.getAnswerCorrect());
        cardLayout.show(mainPanel, "game");
    }

    public void setCategoryPanel(String category1text, String category2text, String category3text, String category4text) {
        categoryButtons.get(0).setText(category1text);
        categoryButtons.get(1).setText(category2text);
        categoryButtons.get(2).setText(category3text);
        categoryButtons.get(3).setText(category4text);
        cardLayout.show(mainPanel, "category");
    }

    public void setInfoPanel(String text) {
        infoText.setText(text);
        cardLayout.show(mainPanel, "info");
    }

    public void updateQuestion(Question question) {
        this.currentQuestion = question;
    }

    public void clearButtonChanges() {
answerButtons.get(0).setBackground(null);
answerButtons.get(1).setBackground(null);
answerButtons.get(2).setBackground(null);
answerButtons.get(3).setBackground(null);
answerButtons.get(0).setBorder(new LineBorder(Color.WHITE));
answerButtons.get(1).setBorder(new LineBorder(Color.WHITE));
answerButtons.get(2).setBorder(new LineBorder(Color.WHITE));
answerButtons.get(3).setBorder(new LineBorder(Color.WHITE));

    }
}
