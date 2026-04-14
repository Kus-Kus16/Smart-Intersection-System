# Smart Intersection System
SIS to symulacja inteligentnych świateł drogowych, która dostosowuje
cykle świateł opierając się na aktualnym natężeniu ruchu na drogach.

## Funkcjonalności
- **Inteligentne światła** - System decyduje które światła powinny być zmienione na podstawie
zatłoczenia oraz czasu od ostatniej zmiany świateł na pasie.
- **Uczestnicy ruchu** - Skrzyżowanie uwzględnia zarówno ruch samochodowy, jak i pieszych.
- **Różne pasy ruchu** - Symulacja obsługuje pas do jazdy prosto i w prawo,
bezkolizyjny pas do skrętu w lewo oraz przejścia dla pieszych. System umożliwia łatwe dodawanie
nowych typów pasów, dzięki systemowi warunkowania kolizji.
- **Własne scenariusze** - Użytkownik może łatwo definiować odtwarzalne scenariusze korzystając z
formatu JSON i predefiniowanych komend. Dodatkowo system zwraca wyniki symulacji w osobnym pliku.
- **Testy** - Aplikacja została objęta testami, które weryfikują poprawność działania kluczowych
elementów symulacji.

## Otwartość systemu
Dzięki modularnej budowie oraz zastosowaniu wzorców i zasad projektowania obiektowego,
projekt jest otwarty na dodawanie nowych funkcjonalności. Obejmuje to nowych uczestników
ruchu, inne rodzaje pasów i świateł, a także definiowanie nowych strategii zarządzania
światłami na podstawie ruchu.

## Algorytm zmiany świateł
Algorytm opiera się na przypisaniu pasom priorytetów w każdym ruchu symulacji. Spośród
dostępnych do zmiany świateł po kolei wybierane są te o najwyższym priorytecie, tak aby
nie kolidowały z pozostałymi pasami.

### 1. Warunkowanie pasów
Na początku odrzucane są te pasy, które w aktualnym kroku nie mogą być zmienione - tj. takie,
których cykl zmiany już się rozpoczął. Przykładowo jeśli światło jest żółte, to w następnym
kroku samo zmieni się na czerwone - nie możemy go więc teraz zmienić.

### 2. Sortowanie pasów
Pozostałe pasy są sortowane według ich aktualnego priorytetu, na który składa się czas oczekiwania
oraz długość kolejki:


$$P_{riority} =
\begin{cases}
\ -\infty & \text{gdy kolejka jest pusta } \\
\ T_{ime}\ \cdot\ S_{ize} & \text{wpp.}
\end{cases}$$
$$S_{ize} = 1 + \frac{n}{10}$$
$$T_{ime} =
\begin{cases}
\ 0.3\ \cdot\ t^2 & \text{gdy światło jest czerwone} \\
\ \frac{12}{t + 1} & \text{wpp.}
\end{cases}$$


Gdzie $t$ jest czasem od zmiany świateł dla użytkowników pasa, a $n$ liczbą użytkowników pasa.

Algorytm ten penalizuje długie oczekiwanie na czerwonym, oraz szybkie zmiany z zielonego światła,
oraz bierze pod uwagę liczność oczekujących osób.

### 3. Grupowanie pasów
Pasy są teraz rozpatrywane w kolejności największych priorytetów, i jeśli nie mają konfliktów z 
włączonymi już pasami - ich światło jest zmieniane na zielone. Dodatkowo pas o największym
priorytecie zabrania włączania innych konfliktujących z nim pasów, nawet jeśli nie uda się go teraz
włączyć. Gwarantuje to szybkie rozładowanie najbardziej obciążonych kierunków.

## Uruchomienie projektu
Pobieramy plik .jar z releases, lub tworzymy go samemu przy użyciu gradle - w katalogu głównym projektu:
```
./gradlew clean jar
```

Teraz uruchamiamy symulację podając plik z komendami oraz plik wyjściowy:
```
java -jar build/libs/sis.jar input.json output.json
```

Jeśli terminal nie obsługuje kolorowania ansi, można podać dodatkowy argument, który je wyłączy:
```
java -jar build/libs/sis.jar input.json output.json false
```

## Format danych
Plik wejściowy oczekuje formatu:
```json
{
  "commands": [
    { 
      "type": "addRoadUser", 
      "userId": "1", 
      "userType": "car", 
      "startRoad": "south", 
      "endRoad": "north"
    },
    { 
      "type": "addRoadUser", 
      "userId": "2", 
      "userType": "car", 
      "startRoad": "south", 
      "endRoad": "north"
    },
    { 
      "type": "step"
    },
    { 
      "type": "addRoadUser", 
      "userId": "3", 
      "userType": "car", 
      "startRoad": "west", 
      "endRoad": "south"
    }
  ]
}
```

Natomiast format wyjściowy to:
```json
{
  "stepStatuses" : [ 
    {
      "leftRoadUsers" : [ "3" ]
    }, 
    {
      "leftRoadUsers" : [  ]
    },
    {
      "leftRoadUsers" : [ "2" ]
    }
  ]
}
```

## Użyte technologie
- Java 21
- Gradle
- JUnit
- Jackson