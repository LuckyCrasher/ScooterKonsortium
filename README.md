# ScooterKonsortium

This Repository contains the files to the Scooter Konsortium Project done by the DI01 at BTR.

403


# Requirements


## java program
### Setup-Mode:
* Erzeugen eines Ladepunktes (User Input)
* Erzeugen einer Firma (User Input)
* Zuordnen Firma und Ladepunkt (User Input)
* Erzeugen eines Scooters (User Input) Scooter darf nicht selbe Koordinaten wie Ladepunkt haben
* Scooter Firma zuordnen (User Input)
* Overview aller Scooter, Ladepunkte und Firmen 
* 2D Bild mit Position der Ladepunkte und Scooter 
* Distanz Berechnung zwischen Scooter und Ladepunkt 
    
### Operative Mode: 
* Aus Datenbank wird gelesen wieviele Ladepunkte es gibt. Entsprechend viele Objekte von "Ladepunkt-Klasse"
* Aus Datenbank wird gelesen wieviele Firmen es gibt. Entsprechend viele Objekte von "Firmen-Klasse"
 Objekt Firma Referenz auf zugeordneten Ladepunkt.
* Aus Datenbank wird gelesen wieviele Scooter es gibt. Entsprechend viele Objekte von "Scooter-Klasse".
 Objekt Scooter Referenz auf zugeordneter Firma.
* Scooter auswähler (User Input) Aktuelle Positionsdaten angezeigt. Ziel fragen x,y Koordinate (User Input). Berechnung ob der Ladezustand reicht.
* Als Distanz orthonogale Entfernung berechnen. Wenn Ladezustand ausreichend, dann Koordinaten ändern und Ladezustand anpassen. Zurückgelegte Strecke erhöhen
* Wenn Scooter ausgewält der Weniger als 30 % hat. Dann nächsten Ladepunkt anzeigen. Scooter kann nicht bewegt werden.
* Benutzer kann Scooter zum Ladepunkt bewegen, Zustand auf "lädt". Ladepunkt Counter höher.
* Akuteller Zustand von allem wird gespeichert.
    
