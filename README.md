# RuinMapper - WIP
A program aiming to make taking notes for complex Metroidvanias like La-Mulana easier than using a spreadsheet program

Also an excuse to try out hexagonal architecture.

The general domain is relatively simple:
- Context: The game the maps and tags are related to, i.e. Super Metroid has some Missile Refill rooms, but those are hardly relevant for Castlevania  
- Area: An area within the game which contains some rooms with X and Y coordinates, like Meridia in Super Metroid
- Room: A room in an area which can contain hints to puzzles or quests to solve.
- Hint: A hint in a room with some personal notes
- Quest: A Quest with some notes and a description
- Tag: For conveniently marking rooms with gamecontext specific Tags.
