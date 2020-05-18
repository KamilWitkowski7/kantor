**Aplikacja prezentuję system, w którym:**
1. Użytkownik może się zarejestrowąć podająć odpowiedni zestaw danych (imię, nazwisko, pesel oraz to jaką kwotą PLN chcę zasilić konto przy rejestracji)
2. Użytkownik następnie może wymienić PLN ze swojego konta na USD, a następnie mając USD może wymienić na PLN
3. Użytkownik musi być pełnoletni, aby móc założyć konto
4. Kurs walut pobierany jest z NBP
5. Konto użytkownika identyfikowane jest przez jego PESEL, wobec czego użytkownik może mieć tylko jedno konto (w aplikacji założono unikalność numeru PESEL)

**Rozwiązanie można zbudować za pomocą komendy:**

`mvn clean install`

W ramach testów aplikacja korzysta z `TestContainers` (https://www.testcontainers.org/) uruchamiając bazę `MongoDb` na chwilę za pomocą kontenera `docker`.

Oprócz tego za pomocą biblioteki `archunit` sprawdzane są reguły przestrzegania architektury heksagonalnej (https://docs.google.com/drawings/d/e/2PACX-1vQ5ps72uaZcEJzwnJbPhzUfEeBbN6CJ04j7hl2i3K2HHatNcsoyG2tgX2vnrN5xxDKLp5Jm5bzzmZdv/pub?w=960&h=657).

W projekcie przyjęto między innymi zasadę, iż w warstwie domeny rozwiązania nie może zostać użyty framework `Spring`.
Dodatkowo testy sprawdzają, czy warstwy są odpowiednio niezleżne.


Dodatkowo przy budowaniu całego rozwiązania w projekcie zostaje uruchomiony automatyczny sprawdzacz kodu dla całego projektu według zasad podanych
w pliku `checkstyle/src/main/resources/google_check.xml`. Aby ułatwić programistom pracę w tym standardzie dodany został plik
`intellij-java-style.xml`, który należy zaimportować do `IntelliJ IDEA`.

Kiedy składnia nie będzie spełniać standardu dostaniemy błędy mówiące co i gdzie trzeba zmienić, przykładowo: https://i.imgur.com/Mki0WNl.png

Jednostką deploymentową jest kontener `docker`. Wersja obrazu jest nadawana dynamicznie za pomocą `jgitver` (https://github.com/jgitver/jgitver).

**Konfiguracja aplikacji**

Najważniejszymi parametrami na start są:

`spring.data.mongodb.account.uri=${SPRING_DATA_MONGODB_LEAD_URI:mongodb://root:example@localhost:27017/admin}`

`mongodb.replace.in-memory=${MONGODB_REPLACE_INMEMORY:false}`

Dotyczą one użycia bazy danych.

W projekcie zastosowano architekture heksagonalną.

Jeżeli chcemy skorzystać z adaptera bazy danych `In memory` wystarczy, że ustawimy jeden z parametrów w ten sposób:

`mongodb.replace.in-memory=${MONGODB_REPLACE_INMEMORY:true}`

Jeżeli chcemy uruchomić aplikację z bazą `MongoDb` możemy na przykład ją uruchomić za pomocą pliku znajdującego się w
folderze `development` poleceniem:

`docker-compose up -d`

**Rozwiązanie testowane zostało na systemie `Linux`.**

**Uruchomienie aplikacji:**

Po zbudowaniu aplikacji i wybraniu opcji z bazą danych uruchamiamy aplikację jak standardową aplikację `SpringBoot`.

Po uruchomieniu aplikacji możemy wejść na stronę http://localhost:8080/swagger-ui.html#/account-controller
Z niej możemy dokonywać akcji w systemie.

Powinien nas uraczyć taki widok: https://i.imgur.com/kr6XAh6.png

Następnie możemy zacząć od akcji założenia konta, a potem akcji wymiany waluty bądź pobrania informacji o koncie.


**Architektura aplikacji**

W aplikacji zostało użyte podejście `CQRS` rozdzielając komendy zapisu jak i odczytu oddzielnie, tak ażeby móc skalować je niezależnie.

W aplikacji została użyta architektura heksagonalna.

W aplikacji zostało użyte podejście `DDD - Domain driven design`.
