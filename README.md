# Flashcard Application Project

## Premise

The project I wish to create is an application aimed at students that uses user-created flashcards to create
quizzes and provide statistics for users to improve their studying experience. It will let one create decks
with flashcards that have a front and back side, review decks of flashcards, and return basic statistics on which
cards they should study more. Because I believe flash cards are among the most efficient study methods, I wanted
to see how I could implement them in a program and integrate further improvements to their efficiency, such as
user-specific statistics.

## P1: Base program
The user stories that I wish to fulfill are:
- As a user, I want to be able to add a flashcard to a deck in a collection of decks
- As a user, I want to be able to edit a specific flashcard in a deck
- As a user, I want to be able to be tested on the flashcards from a deck of my choice
- As a user, I want to be able to see which flashcards I got incorrect

## P2: Saving and loading

- As a user, I want to be able to save a deck to a file
- As a user, I want to be able to load a deck from a file
 
###P3: Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by clicking the "Create Deck" button on the
  Home menu. Enter a deck name and course when prompted. Then, from the dropdown
  menu to the right of "Selected Deck", selected the deck you just create. Then, a new window on the right will appear
  where you can add FlashCards to a Deck.
  - Note: To properly select a Deck or FlashCard, make sure to click on it from the dropdown menu
- You can generate the second required event related to adding Xs to a Y by selecting a flashcard from a deck and then
  clicking the "Edit Flashcard" on the deck menu. You can then replace the selected card with a newly delegated front
  and back.
- You can locate my visual component by looking for the deck icon to the left of the "Selected Deck" label on the
  Home Menu, and also by looking beside "Selected Card" after selecting a deck.
- You can save the state of my application by clicking the "Save Deck" button on the Home Menu, or by pressing ctrl + s.
  The deck is then saved under a file named as a conjunction of the deck's name and course.
- You can reload the state of my application by selecting the "Import Deck" button on the Home Menu, or by pressing
  ctrl + I. Then, they can input the filename of the .json file, such as "DNABIOL200," which already exists in this
  project as one of the sample files. 

## Phase 4: Task 2
Below is an example of a log output. The line dividers and hashtags are part of the actual outputted logs because it
aided with readability in the console.

########################  
Log Start
---
Fri Dec 02 13:49:54 PST 2022  
Created deck with name: Suzette, course: CPSC210
---
Fri Dec 02 13:49:58 PST 2022  
Loaded deck from location ./data/DNABIOL200.json
---
Fri Dec 02 13:49:58 PST 2022  
Created deck with name: DNA, course: BIOL200
---
Fri Dec 02 13:49:58 PST 2022  
Added flashcard with front: Pairs with A?, back: T to deck name: DNA, course: BIOL200
---
Fri Dec 02 13:49:58 PST 2022  
Added flashcard with front: Pairs with C?, back: G to deck name: DNA, course: BIOL200
---
Fri Dec 02 13:49:59 PST 2022  
Saved deck with name: Suzette, course: CPSC210 to location ./data/SuzetteCPSC210.json
---
Fri Dec 02 13:50:14 PST 2022  
Added flashcard with front: thanks, back: for the help to deck name: Suzette, course: CPSC210
---
Fri Dec 02 13:50:22 PST 2022  
Added flashcard with front: best, back: TA to deck name: Suzette, course: CPSC210
---
Fri Dec 02 13:50:22 PST 2022  
Removed flashcard with front: thanks, back: for the help from deck name: Suzette, course: CPSC210
---
Fri Dec 02 13:50:22 PST 2022  
Removed flashcard with front: thanks, back: for the help from deck name: Suzette, course: CPSC210
---
Fri Dec 02 13:50:28 PST 2022  
Removed flashcard with front: best, back: TA from deck name: Suzette, course: CPSC210
---
Application exited

## Phase 4: Task 3

- DeckUI should just refer to its stored Deck to get a list of FlashCards instead of storing that same Deck's
  FlashCards in another list. That way, DeckUI would not have to store another duplicate
instance of a FlashCard list.
- Have FlashCardAppUI's selected Deck follow the Singleton design pattern. This should ensure that
the DeckUI window's actions affect the one selected Deck, instead of creating a new DeckUI
for every different selected Deck like it current does now.
- Make an abstract class that converts FlashCards and Decks to string form. Then, the Deck, JsonReader, JsonWriter,
FlashCardAppUI, and DeckUI would extend that class to inherit its behaviour which all of these classes
use frequently, but without a streamlined method. Adding this abstract class would ensure that if I wanted to make changes, I
could have one point of control to the print method implementation. 
- Follow the Composite design pattern where the Deck is a composite of components. The FlashCard
class would extend the component class so that the Deck may store many FlashCards as well as other types of
components that I may wish to implement in the future, such as different card classes that contain images or sounds
instead of just strings.