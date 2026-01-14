# Introduction

Our game is simply Reversi, also known as Othello. The difference between the two names is that Reversi is a royalty-free, open-source name, while Othello is a name registered by a Japanese company in 1971. 
Reversi was invented relatively late, around the 1880s, by Englishmen L. Waterman and JW. Mollett. 
It appears to be the first game to have used the principle of flipping pieces. The game consists of taking turns 
and “trapping” as many of your opponent's pieces as possible. When the board is full, the player with the most pieces of their color wins.

(Source : https://fr.wikipedia.org/wiki/Othello_(jeu))
#  Instructions:
## Starting the game
1. At the start, four pieces are placed in the center of the board: two white and two black.
<img width="300" height="300" alt="Capture d’écran 2026-01-14 093123" src="https://github.com/user-attachments/assets/7b7e2931-c28c-4aa8-bcfd-36289a665a40" />

## How to play
1. The player with the black pieces always starts the game.
2. Each player takes turns trying to capture as many of their opponent's pieces as possible.

   A piece is captured when a player places one of their pieces at the end of a line of their opponent's pieces. This is provided that the other end is occupied by a piece of the same color as the player's.
4. They can be captured in rows, columns, diagonals, or all three at the same time.
5. When a player has no opportunity to capture pieces of the opposite color, they are forced to pass their turn.
   
## End of the game
There are two ways to end the game: 
1. There are no more free squares and the player with the most pieces of their color wins the game.
2. If neither player can make a move, the player with the most pieces is declared the winner.
<img width="1264" height="899" alt="image (3)" src="https://github.com/user-attachments/assets/6f50a30b-a76b-49c2-8a37-94ab1c619634" />


# Code structure
## In folder "src"

We have listed the files according to their usefulness.

- animation:  here you will find the file that animates the color change of the piece

- class: we have stored all the information concerning the “objects” that we have used, whether for managing the players, the sound, or each square of the othelier.  

- Font: here you will find all the fonts used in this project, such as the names of the players and the score.

- game: contains the executable files for our project.

- img: contains all images used in the project.

- listener: contains all detections made by users.

- sounds: contains all the soundtracks used for the project.

- utilis: contains the companion objects for the project.


# Development team:
- Adrien Gaillard: adrien.gaillard@students.hevs.ch
- Ravaka Nasandratriniaina Andriamifidy: nasandratriniaina.andriamifidy@students.hevs.ch
