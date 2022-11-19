package ui;

import java.awt.Component;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import model.FlashCard;

public class FlashCardPrinter extends JInternalFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private JTextArea textArea;

    public FlashCardPrinter(Component parent) {
        super("FlashCard list", false, true, false, false);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }

    // EFFECTS: prints flashcard from lof
    public void printFlashCards(List<FlashCard> lof) {
        for (FlashCard f : lof) {
            textArea.setText(textArea.getText() + "Front: " + f.getFront() + ", Back: " + f.getBack() + "\n\n");
        }
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: sets flashcard print window position
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20,
                parent.getHeight() - getHeight() - 20);
    }
}
