package ui;

import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DeckUI extends JInternalFrame {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private Deck deck;
    private Component theParent;
    private JComboBox<String>  cardSelectorCombo;

    /**
     * Constructor
     * @param d   the deck
     * @param parent  the parent component
     */
    public DeckUI(Deck d, Component parent) {
        super("Deck", false, false, false, false);
        JPanel deckPanel = new JPanel();
        JLabel selectorLabel = new JLabel("Selected Card:");
        deck = d;
        theParent = parent;
        this.setLayout(new GridLayout(4, 2));

        this.add(selectorLabel);
        this.add(createCardSelectionCombo());
        this.add(new JButton(new AddCardAction()));
        this.add(new JButton(new EditCardAction()));
        this.add(new JButton(new RemoveCardAction()));
        this.add(new JButton(new SaveDeckAction()));
        this.add(new JButton(new ReviewDeckAction()));
        this.add(new JButton(new DeleteDeckAction()));

        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }


    private JComboBox<String> createCardSelectionCombo() {
        cardSelectorCombo = new JComboBox<String>();
        return cardSelectorCombo;
    }

    private class AddCardAction extends AbstractAction {

        AddCardAction() {
            super("Add card");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class RemoveCardAction extends AbstractAction {

        RemoveCardAction() {
            super("Remove card");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DeleteDeckAction extends AbstractAction {

        DeleteDeckAction() {
            super("Delete deck");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class SaveDeckAction extends AbstractAction {

        SaveDeckAction() {
            super("Save deck");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class EditCardAction extends AbstractAction {

        EditCardAction() {
            super("Edit card");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ReviewDeckAction extends AbstractAction {

        ReviewDeckAction() {
            super("Review");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


    /**
     * Sets the position of this remote control UI relative to parent component
     * @param parent   the parent component
     */
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth(), 0);
    }


}

