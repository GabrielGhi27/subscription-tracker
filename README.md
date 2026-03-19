#  SubTracker - Subscription Management System

> O aplicatie web Full-Stack moderna, conceputa pentru a ajuta utilizatorii sa isi gestioneze inteligent abonamentele recurente (ex: Netflix, Spotify, sala de fitness), oferind analitice financiare, conversie valutara in timp real si notificari automate pe email.

---

## Live demo: https://subscription-tracker-webv.onrender.com/

## ✨ Functionalitati Principale

* **🔐 Securitate si Autentificare (Stateless):** Sistem complet de Inregistrare/Logare folosind tokenuri JWT (JSON Web Tokens) salvate in `LocalStorage` si parole criptate cu BCrypt.
* **📊 Dashboard Financiar & Analitice:** Calcul automat al cheltuielilor lunare si anuale, alaturi de identificarea celui mai scump abonament. Include un grafic interactiv (*Doughnut Chart*) pentru vizualizarea distributiei bugetului.
* **💱 Conversie Valutara Automata:** Integrare cu API extern pentru a converti automat orice suma (EUR, USD etc.) in RON, oferind un total real si precis al cheltuielilor.
* **🔄 Gestiune Completa (CRUD):** Utilizatorii pot adauga, vizualiza, edita (pe loc, prin modal) si sterge abonamente.
* **⏳ Sistem Inteligent de Sortare & Alerte:** Lista este ordonata automat in functie de data urmatoarei plati. Include *Badges* colorate care indica urgenta (ex: „Scade in 3 zile!”, „Plata intarziata!”).
* **📧 Notificari Asincrone pe Email:**
    * Email automat de „Bun venit” la crearea contului.
    * *Cron Job* zilnic (la ora 00:00) care trimite remindere pe email pentru abonamentele care expira a doua zi.
    * Procesare `@Async` pentru a nu bloca experienta utilizatorului in interfata.
* **🎨 Interfata Grafica Premium (UI/UX):**
    * Design modern *Dark Mode* cu efecte de *Glassmorphism* (sticla mata) pe bara de navigatie.
    * Sistem de Pop-up-uri (Modals) animate cu efect de *blur* pentru fundal la adaugarea/editarea abonamentelor.
    * Notificari de tip *Toast* (animale, cu glisare) pentru confirmarea actiunilor.
    * Avatar generat automat pe baza initialei email-ului utilizatorului.
    * Recunoastere inteligenta a brandurilor (asigneaza automat iconite specifice pentru Netflix, Spotify, servicii de internet etc.).

---

## 🛠️ Tech Stack (Tehnologii Folosite)

### Backend
* **Java 21**
* **Spring Boot 3**
* **Spring Security** (Protectie rute, Filtre JWT personalizate)
* **Spring Data JPA / Hibernate** (ORM)
* **Spring Boot Mail** (JavaMailSender)
* **Spring Scheduling & Async** (`@Scheduled`, `@Async`)

### Frontend
* **HTML5 & CSS3** (Custom CSS, Flexbox, CSS Grid, Animatii `@keyframes`)
* **Vanilla JavaScript (ES6+)** (Fetch API, DOM Manipulation asincron, Arhitectura Single Page Application)

### Baza de Date
* **PostgreSQL** (Gazduita in Cloud / Local)

---

## 🔌 API-uri si Librarii Externe Integrate

1. **ExchangeRate-API** - Folosit direct din Spring Boot (`RestTemplate`) pentru a prelua cursurile valutare la zi si a converti cheltuielile in moneda de baza (RON).
2. **Chart.js** - Librarie JavaScript folosita pe frontend pentru a randa graficul interactiv al cheltuielilor.
3. **FontAwesome (v6.4.0)** - Librarie de iconite vectoriale utilizata pentru UI, avatare, butoane si recunoasterea automata a brandurilor.
4. **JJWT (io.jsonwebtoken)** - Pentru generarea, semnarea si parsarea tokenurilor de securitate.

---

## 🛡️ Securitate si Arhitectura
* **Protectie IDOR:** Backend-ul verifica la fiecare cerere de Editare/Stergere daca resursa accesata (abonamentul) apartine strict utilizatorului autentificat.
* **Variabile de Mediu:** Datele sensibile (URL baza de date, parole email, secret JWT) sunt extrase din variabilele de mediu (`${VARIABILA:fallback}`), protejand codul sursa.
* **Arhitectura Monolit:** Frontend-ul este servit direct de aplicatia Spring Boot din folderul `resources/static`, permitand utilizarea de rute relative (`/api/...`) si evitand problemele de CORS la deployment.

---

## 🚀 Instalare si Rulare Locala

### Preconditii
* Java 21 instalat
* PostgreSQL instalat si ruland pe portul implicit (5432)
* Maven (sau wrapper-ul inclus `mvnw`)

### Pasi

1. **Cloneaza repository-ul:**
```bash
git clone [https://github.com/NUMELE_TAU/subscription-tracker.git](https://github.com/NUMELE_TAU/subscription-tracker.git)
cd subscription-tracker
```

2. **Configureaza Baza de Date:**
   Creeaza o baza de date in PostgreSQL cu numele `subscription_tracker`.
   *(Optional: Modifica userul si parola in `application.properties` sau seteaza variabilele de mediu `DB_USER` si `DB_PASSWORD`).*

3. **Ruleaza aplicatia:**
   Folosind Maven Wrapper:
```bash
./mvnw spring-boot:run
```
*(Pe Windows foloseste `mvnw.cmd spring-boot:run`)*

4. **Acceseaza aplicatia:**
   Deschide browser-ul si navigheaza la:
   `http://localhost:8080/index.html`

---
