# 6.4

| MitarbeiterNr | Abteilung | ChefNr | Kunde | Ware | Anzahl |
| :---: | :---: | :---: | :---: | :---: | :---: |
| 11 | VT | 5 | Miele | Dichtung | 15 |
| 11 | VT | 5 | Bosch | TFT | 51 |
| 11 | VT | 5 | Miele | Drehschalter | 55 |
| 21 | VT | 5 | DB | Trafo | 58 |
| 21 | VT | 5 | Miele | Dichtung | 55 |
| 28 | EN | 8 | SEW | Wicklung | 70 |


# 6.5
{% comment %}
## 2NF
### Mitarbeiter

| MitarbeiterNr | Abteilung | ChefNr |
| :---: | :---: | :---: |
| 11 | VT | 5 |
| 21 | VT | 5 |
| 28 | EN | 8 |

### Bestellung

| MitarbeiterNr | Kunde | Ware | Anzahl |
| :---: | :---: | :---: | :---: |
| 11 | Miele | Dichtung | 15 |
| 11 | Bosch | TFT | 51 |
| 11 | Miele | Drehschalter | 55 |
| 21 | DB | Trafo | 58 |
| 21 | Miele | Dichtung | 55 |
| 28 | SEW | Wicklung | 70 |
{% endcomment %}
## 3NF
### Mitarbeiter

| MitarbeiterNr | Abteilung |
| :---: | :---: |
| 11 | VT |
| 21 | VT |
| 28 | EN |

### Chef

| Abteilung | ChefNr |
| :---: | :---: |
| VT | 5 |
| EN | 8 |

### Bestellung

| MitarbeiterNr | Ware | Anzahl |
| :---: | :---: | :---: |
| 11 |Dichtung | 15 |
| 11 | TFT | 51 |
| 11 | Drehschalter | 55 |
| 21 | Trafo | 58 |
| 21 | Dichtung | 55 |
| 28 | Wicklung | 70 |

### Produkt

| Ware | Kunde |
| :---: | :---: |
| Dichtung | Miele |
| TFT | Bosch |
| Drehschalter | Miele |
| Trafo | DB |
| Wicklung | SEW |
