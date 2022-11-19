package ui;

import exceptions.DuplicateFlashCardException;
import model.Deck;
import model.FlashCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class DeckUI extends JInternalFrame implements ActionListener, MessageHandler {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 200;
    private Deck deck;
    private List<FlashCard> cardsInDeck;

    private Component theParent;
    private JComboBox<String> cardSelectorCombo;
    private HashMap<String, FlashCard> cardSelectorReference;
    private FlashCard selectedFlashCard;
    private String reference;

    /**
     * Constructor
     *
     * @param d      the deck
     * @param parent the parent component
     */
    public DeckUI(Deck d, Component parent) {
        super("Deck " + d.getName(), true, true, false, false);
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
        this.add(new JButton(new PrintCardsInDeckAction()));


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
            updateSelectedCard();
        }
    }


    private void updateSelectedCard() {
        reference = (String) cardSelectorCombo.getSelectedItem();
        selectedFlashCard = cardSelectorReference.get(reference);
        System.out.println("reference:" + reference);
        System.out.println("selected:" + cardSelectorCombo.getSelectedItem());
        System.out.println("stored:" + selectedFlashCard); // not storing updated
        System.out.println("is null?" + isSelectedNull());
        System.out.println("STRART PRINT");
        for (String name: cardSelectorReference.keySet()) {
            String value = cardSelectorReference.get(name).toString();
            System.out.println(name + " " + value);
        }
        System.out.println("END PRINT");
    }


    private void updateAddCardSelectionCombo(FlashCard f) {
        String name = getCardName(f);
        cardSelectorCombo.addItem(name);
        cardSelectorReference.put(name, f);
        cardSelectorCombo.setSelectedIndex(0);
        updateSelectedCard();
    }


    private void updateRemoveCardSelectionCombo() {
        cardSelectorCombo.removeItem(cardSelectorCombo.getSelectedItem());
        cardSelectorReference.remove(reference);
        deck.removeFlashCard(selectedFlashCard);
        cardSelectorCombo.setSelectedIndex(0);
        updateSelectedCard();
    }

    public void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

    public void showErrorDialog(Exception e, String title) {
        JOptionPane.showMessageDialog(null, e.getMessage(), title,
                JOptionPane.ERROR_MESSAGE);
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
                        showErrorDialog(e, "Add failed");
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
            updateRemoveCardSelectionCombo();
        }
    }


    private class PrintCardsInDeckAction extends AbstractAction {

        PrintCardsInDeckAction() {
            super("Print all cards");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCardPrinter fcp;
            fcp = new FlashCardPrinter(DeckUI.this);
            getDesktopPane().add((FlashCardPrinter) fcp);
            fcp.setLocation(getDesktopPane().getWidth() - getWidth(), getHeight());
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
                            replaceCard(cardToAdd);
                        } catch (DuplicateFlashCardException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }

        private void replaceCard(FlashCard cardToAdd) throws DuplicateFlashCardException {
            try {
                deck.addFlashCard(cardToAdd);
                updateAddCardSelectionCombo(cardToAdd);
                updateRemoveCardSelectionCombo();
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

    private static String getCardName(FlashCard f) {
        String name = f.getFront() + " : " + f.getBack();
        return name;
    }

}

