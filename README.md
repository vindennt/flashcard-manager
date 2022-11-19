# Flashcard Application Project

## Premise

The project I wish to create is an application aimed at students that uses user-created flashcards to create
quizzes and provide statistics for users to improve their studying experience. It will let one create decks
with flashcards that have a front and back side, review decks of flashcards, and return basic statistics on which
cards they should study more. Because I believe flash cards are among the most efficient study methods, I wanted
to see how I could implement them in a program and integrate further improvements to their efficiency, such as
user-specific statistics.

## User Stories
### P1: Base program
The user stories that I wish to fulfill are:
- As a user, I want to be able to add a flashcard to a deck in a collection of decks
- As a user, I want to be able to edit a specific flashcard in a deck
- As a user, I want to be able to be tested on the flashcards from a deck of my choice
- As a user, I want to be able to see which flashcards I got incorrect

### P2: Saving and loading

- As a user, I want to be able to save a deck to a file
- As a user, I want to be able to load a deck from a file
 
# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by clicking the "Create Deck" button on the
  Home menu. Enter a deck name and course when prompted. Then, from the dropdown
  menu to the right of "Selected Deck", selected the deck you just create. Then, a new window on the right will appear
  where you can add FlashCards to a Deck.
  - Note: To properly select a Deck or FlashCard, make sure to click on it from the dropdown menu
- You can generate the second required event related to adding Xs to a Y by clicking the "Print decks from course"
  button on the Home Menu. You can input the name of a course to filter by so that the program will print out
  only the names of decks that are from the input course, which can help you narrow down your search
- You can locate my visual component by looking for the deck icon to the left of the "Selected Deck" label on the
  Home Menu, and also by looking beside "Selected Card" after selecting a deck.
- You can save the state of my application by clicking the "Save Deck" button on the Home Menu, or by pressing ctrl + s.
  The deck is then saved under a file named as a conjunction of the deck's name and course.
- You can reload the state of my application by selecting the "Import Deck" button on the Home Menu, or by pressing
  ctrl + I. Then, they can input the filename of the .json file, such as "DNABIOL200," which already exists in this project as one of the sample
  files. 


