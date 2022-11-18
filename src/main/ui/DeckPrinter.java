package ui;

import java.awt.Component;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Deck;
import model.FlashCard;

/**
 * Represents a screen printer for printing event log to screen.
 */
public class DeckPrinter extends JInternalFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private JTextArea logArea;

    /**
     * Constructor sets up window in which log will be printed on screen
     * @param parent  the parent component
     */
    public DeckPrinter(Component parent) {
        super("Deck list", false, true, false, false);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }

    public void printDeck(List<Deck> lod) {
        for (Deck d : lod) {
            //logArea.setText(logArea.getText() + d.toString() + "\n\n");
            logArea.setText(logArea.getText() + "Name: " + d.getName() + ", Course: " + d.getCourse()
                    + ", FlashCards: " + d.getCardsInDeck().size() + "\n\n");
        }
        repaint();
    }

    /**
     * Sets the position of window in which log will be printed relative to
     * parent
     * @param parent  the parent component
     */
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20,
                parent.getHeight() - getHeight() - 20);
    }
}
