package ui;

import exceptions.DuplicateFlashCardException;
import model.Deck;
import model.FlashCard;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class DeckUI extends JInternalFrame implements ActionListener {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;
    private Deck deck;
    private List<FlashCard> cardsInDeck;

    private Component theParent;
    private JComboBox<String> cardSelectorCombo;
    private HashMap<String, FlashCard> cardSelectorReference;
    private FlashCard selectedFlashCard;

    private JsonWriter jsonWriter;    // deck saver
    private JsonReader jsonReader;    // deck loader
    private String jsonFileLocation;  // tracks target file location

    /**
     * Constructor
     *
     * @param d      the deck
     * @param parent the parent component
     */
    public DeckUI(Deck d, Component parent) {
        super("Deck", true, true, false, false);
        JLabel selectorLabel = new JLabel("Selected Card:");
        deck = d;
        theParent = parent;
        this.setLayout(new GridLayout(4, 2));
        cardSelectorReference = new HashMap<>();

        this.add(selectorLabel);
        this.add(createCardSelectionCombo());
        this.add(new JButton(new AddCardAction()));
        this.add(new JButton(new EditCardAction()));
        this.add(new JButton(new RemoveCardAction()));
        this.add(new JButton(new ReviewDeckAction()));
        this.add(new JButton(new PrintDeckAction()));


        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }


    private JComboBox<String> createCardSelectionCombo() {
        cardSelectorCombo = new JComboBox<String>();
        cardSelectorCombo.setEditable(true);
        cardSelectorCombo.addActionListener(this);

        cardsInDeck = deck.getCardsInDeck();
        for (FlashCard f : cardsInDeck) {
            updateAddCardSelectionCombo(f);
        }
        return cardSelectorCombo;
    }

    private boolean isSelectedNull() {
        return (selectedFlashCard.getFront().isBlank());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cardSelectorCombo)) {
            System.out.println(cardSelectorCombo.getSelectedItem());
            String reference = (String) cardSelectorCombo.getSelectedItem();
            selectedFlashCard = cardSelectorReference.get(reference);
        }
    }

    private void updateAddCardSelectionCombo(FlashCard f) {
        String name = f.getFront() + " : " + f.getBack();
        cardSelectorCombo.addItem(name);
        cardSelectorReference.put(name, f);
    }

    private void updateRemoveCardSelectionCombo(FlashCard f) {
        if (cardsInDeck.size() == 1) {
            deck.removeFlashCard(selectedFlashCard);
            cardSelectorCombo.removeAllItems();
        } else {
            String name = (f.getFront() + " : " + f.getBack());
            deck.removeFlashCard(selectedFlashCard);
            cardSelectorCombo.removeItem(name);
            cardSelectorReference.remove(name, f);
            System.out.println(deck.size());
        }
    }

    private class AddCardAction extends AbstractAction {

        AddCardAction() {
            super("Add card");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String front;
            String back;

            front = JOptionPane.showInputDialog(null,
                    "Card front (The question): ",
                    "Card creation",
                    JOptionPane.QUESTION_MESSAGE);

            if (front != null) {
                back = JOptionPane.showInputDialog(null,
                        "Card back (The answer): ",
                        "Card creation",
                        JOptionPane.QUESTION_MESSAGE);

                if (back != null) {
                    FlashCard cardToAdd = new FlashCard(front, back);
                    try {
                        deck.addFlashCard(cardToAdd);
                        updateAddCardSelectionCombo(cardToAdd);
                    } catch (DuplicateFlashCardException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Added flashcard with front: " + front + ", back: " + back + " to deck.");
                }
            }
        }
    }

    private class RemoveCardAction extends AbstractAction {

        RemoveCardAction() {
            super("Remove card");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            updateRemoveCardSelectionCombo(selectedFlashCard);
        }
    }


    private class PrintDeckAction extends AbstractAction {

        PrintDeckAction() {
            super("Print all cards");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCardPrinter fcp;
            fcp = new FlashCardPrinter(DeckUI.this);
            fcp.setLocation(50, getHeight()  / 2);
            getDesktopPane().add((FlashCardPrinter) fcp);
            fcp.printFlashCards(cardsInDeck);
        }
    }


    private class EditCardAction extends AbstractAction {

        EditCardAction() {
            super("Edit card");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String front;
            String back;
            if (isSelectedNull()) {
                JOptionPane.showMessageDialog(null, "No card selected", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                front = JOptionPane.showInputDialog(null, "New card front (The question): ",
                        "Card Edit",
                        JOptionPane.QUESTION_MESSAGE);

                if (front != null) {
                    back = JOptionPane.showInputDialog(null, "Card back (The answer): ",
                            "Card Edit",
                            JOptionPane.QUESTION_MESSAGE);

                    if (back != null) {
                        FlashCard cardToAdd = new FlashCard(front, back);
                        try {
                            replaceCard(selectedFlashCard, cardToAdd);
                        } catch (DuplicateFlashCardException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }

        private void replaceCard(FlashCard cardToRemove, FlashCard cardToAdd) throws DuplicateFlashCardException {
            try {
                deck.addFlashCard(cardToAdd);
                updateAddCardSelectionCombo(cardToAdd);
                deck.removeFlashCard(cardToRemove);
                updateRemoveCardSelectionCombo(cardToRemove);
            } catch (DuplicateFlashCardException e) {
                throw e;
            }
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
     *
     * @param parent the parent component
     */
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth(), 0);
    }


}

