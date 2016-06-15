# NorthernForce-Scouting-App
Android App to be used for scouting.

Database Schema:

config file located in assets folder

Teams
int Team_Number
String Defining_Characteristics
int Average_Score
String High_School

Matches
int Team_Number
int Match_Number
String Event_Name
int Allie_One (team number)
int Allie_Two (team number)
int Opponent_One (team number)
int Opponent_Two (team number)
int Opponent_Three (team number)

Performance
int Team_Number
int Match_Number
int Score
int Performance


Events
String Event_Name
String Date
String Location