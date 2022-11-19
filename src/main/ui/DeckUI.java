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

// Deck UI for interacting with the flashcards in a deck
public class DeckUI extends JInternalFrame implements ActionListener, MessageHandler {
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

    public DeckUI(Deck d, Component parent) {
        super("Deck: " + d.getName(), true, true, false, false);
        JLabel selectorLabel = new JLabel("Selected Card:");

        ImageIcon imageIcon =
                new ImageIcon(new ImageIcon("data/cardIcon.png").getImage().getScaledInstance(25,
                        25, Image.SCALE_DEFAULT));
        selectorLabel.setIcon(imageIcon);

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

    // EFFECTS: creates the dropdown menu for all flashcards
    private JComboBox<String> createCardSelectionCombo() {
        cardSelectorCombo = new JComboBox<String>();
        cardSelectorCombo.setEditable(true);
        cardSelectorCombo.addActionListener(this);

        updateCardSelectionAndReference();
        return cardSelectorCombo;
    }

    // MODIFIES: this
    // EFFECTS: tracks actions on the dropdown menu and stores the currently selected flashcard
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cardSelectorCombo)) {
            System.out.println(cardSelectorCombo.getSelectedItem());
            String reference = (String) cardSelectorCombo.getSelectedItem();
            selectedFlashCard = cardSelectorReference.get(reference);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates card selection dropdown and the hashmap reference
    private void updateCardSelectionAndReference() {
        cardsInDeck = deck.getCardsInDeck();
        for (FlashCard f : cardsInDeck) {
            updateAddCardSelectionCombo(f);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the dropdown menu and flashcard hashmap when adding cards
    private void updateAddCardSelectionCombo(FlashCard f) {
        String name = f.getFront() + " : " + f.getBack();
        cardSelectorCombo.addItem(name);
        cardSelectorReference.put(name, f);
    }

    // MODIFIES: this
    // EFFECTS: updates the dropdown menu and flashcard hashmap when removing cards
    private void updateRemoveCardSelectionCombo(FlashCard f) {
        deck.removeFlashCard(selectedFlashCard);
        cardSelectorCombo.removeAllItems();
        cardSelectorReference = new HashMap<>();
        updateCardSelectionAndReference();
    }

    // MODIFIES: this
    // EFFECTS:  displays messsage box
    public void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: displays error message box
    public void showErrorDialog(Exception e, String title) {
        JOptionPane.showMessageDialog(null, e.getMessage(), title,
                JOptionPane.ERROR_MESSAGE);
    }


    private class AddCardAction extends AbstractAction {

        AddCardAction() {
            super("Add card");
        }

        @Override
        // MODIFIES: this
        // EFFECTS: prompts user to create a flashcard.
        // throws DuplicateFlashCardException when trying to create a flashcard that already exists
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

        // MODIFIES: this
        // EFFECTS: removes the selected flashcard from the deck
        @Override
        public void actionPerformed(ActionEvent e) {
            updateRemoveCardSelectionCombo(selectedFlashCard);
        }
    }


    private class PrintDeckAction extends AbstractAction {

        PrintDeckAction() {
            super("Print all cards");
        }


        // EFFECTS: prints out all the flashcards in the selected deck.
        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCardPrinter fcp;
            fcp = new FlashCardPrinter(DeckUI.this);
            fcp.setLocation(50, getHeight() / 2);
            getDesktopPane().add((FlashCardPrinter) fcp);
            fcp.printFlashCards(cardsInDeck);
        }
    }


    private class EditCardAction extends AbstractAction {

        EditCardAction() {
            super("Edit card");
        }

        @Override
        // MODIFIES: this
        // EFFECTS: prompts user to input new text for their selected flashcard. If there is no card selected,
        // then the edit function will attempt to create the input card.
        public void actionPerformed(ActionEvent event) {
            String front;
            String back;
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

    // MODIFIES: this
    // EFFECTS: replaces selected flashcard with new edited version
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


    private class ReviewDeckAction extends AbstractAction {

        ReviewDeckAction() {
            super("Review");
        }

        // EFFECTS: Displays the flashcards in quiz style for the user to answer.
        @Override
        public void actionPerformed(ActionEvent e) {
            showMessageDialog("Under construction :)", "Sorry");
        }

    }


    // MODIFIES: this
    // EFFECTS: sets position of the deckUI relative to the parent frame
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth(), 0);
    }
}

