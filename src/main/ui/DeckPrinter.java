// References: AlarmSystem
// Git: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// Author: Paul Carter
// Contribution: Adapted to work for FlashCardApp methods

package ui;

import java.awt.Component;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Deck;

// Represents a printer to view existing deck information in a window
public class DeckPrinter extends JInternalFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private JTextArea textArea;

    public DeckPrinter(Component parent) {
        super("Deck list", false, true, false, false);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }

    // EFFECTS: prints deck from lod
    public void printDeck(List<Deck> lod) {
        for (Deck d : lod) {
            textArea.setText(textArea.getText() + "Name: " + d.getName() + ", Course: " + d.getCourse()
                    + ", FlashCards: " + d.getCardsInDeck().size() + "\n\n");
        }
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: sets position of the deck printer window
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20,
                parent.getHeight() - getHeight() - 20);
    }
}
