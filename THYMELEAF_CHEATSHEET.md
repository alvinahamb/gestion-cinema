# Thymeleaf Cheatsheet

## 1. Configuration de base

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title th:text="${titre}">Titre par défaut</title>
</head>
<body>
    <!-- Contenu -->
</body>
</html>
```

## 2. Affichage de texte

```html
<!-- Remplacer tout le contenu -->
<p th:text="${message}">Texte par défaut</p>

<!-- Insérer du texte non-échappé (HTML) -->
<p th:utext="${htmlContent}"></p>

<!-- Concaténation -->
<p th:text="'Bonjour ' + ${nom}"></p>
<p th:text="|Bonjour ${nom}!|"></p>
```

## 3. Attributs

```html
<!-- href -->
<a th:href="@{/langues}">Liste des langues</a>
<a th:href="@{/films/{id}(id=${film.idFilm})}">Détails</a>
<a th:href="@{/films(page=${page},size=10)}">Page suivante</a>

<!-- src -->
<img th:src="@{/images/logo.png}" alt="Logo">

<!-- class -->
<div th:class="${actif} ? 'actif' : 'inactif'">Statut</div>
<div th:classappend="${error} ? 'error' : ''">Message</div>

<!-- style -->
<div th:style="'color:' + ${couleur}">Texte coloré</div>

<!-- Attributs personnalisés -->
<input th:value="${client.nom}" type="text">
<input th:checked="${abonne}" type="checkbox">
<option th:selected="${pays.idPays == film.idPays}">...</option>
```

## 4. Conditions

```html
<!-- if -->
<div th:if="${films != null}">
    Il y a des films
</div>

<!-- unless (inverse de if) -->
<div th:unless="${films == null}">
    Il y a des films
</div>

<!-- if-else avec th:block -->
<th:block th:if="${films.isEmpty()}">
    <p>Aucun film trouvé</p>
</th:block>
<th:block th:unless="${films.isEmpty()}">
    <p>Films disponibles</p>
</th:block>

<!-- Opérateur ternaire -->
<span th:text="${actif} ? 'Actif' : 'Inactif'"></span>

<!-- Elvis operator (défaut si null) -->
<span th:text="${nom} ?: 'Anonyme'"></span>
```

## 5. Boucles

```html
<!-- Itération simple -->
<tr th:each="film : ${films}">
    <td th:text="${film.titre}"></td>
    <td th:text="${film.duree}"></td>
</tr>

<!-- Avec status (index, count, etc.) -->
<tr th:each="film, stat : ${films}">
    <td th:text="${stat.count}">1</td>
    <td th:text="${film.titre}"></td>
    <td th:text="${stat.even} ? 'Pair' : 'Impair'"></td>
</tr>

<!-- Variables de status -->
<!-- stat.index: index (commence à 0) -->
<!-- stat.count: numéro (commence à 1) -->
<!-- stat.size: nombre total d'éléments -->
<!-- stat.current: élément courant -->
<!-- stat.even/odd: pair/impair -->
<!-- stat.first/last: premier/dernier -->
```

## 6. Formulaires

```html
<!-- Formulaire avec objet -->
<form th:action="@{/films/save}" th:object="${film}" method="post">
    <input type="hidden" th:field="*{idFilm}">
    
    <label>Titre:</label>
    <input type="text" th:field="*{titre}">
    
    <label>Synopsis:</label>
    <textarea th:field="*{synopsis}"></textarea>
    
    <label>Durée:</label>
    <input type="number" th:field="*{duree}">
    
    <label>Date de sortie:</label>
    <input type="date" th:field="*{dateSortie}">
    
    <label>Langue:</label>
    <select th:field="*{idLangue}">
        <option th:each="langue : ${langues}" 
                th:value="${langue.idLangue}" 
                th:text="${langue.nom}">
        </option>
    </select>
    
    <label>Actif:</label>
    <input type="checkbox" th:field="*{actif}">
    
    <button type="submit">Enregistrer</button>
</form>

<!-- Formulaire sans objet -->
<form th:action="@{/search}" method="get">
    <input type="text" name="query" th:value="${query}">
    <button type="submit">Rechercher</button>
</form>

<!-- Bouton de suppression -->
<form th:action="@{/films/delete/{id}(id=${film.idFilm})}" method="post">
    <button type="submit" onclick="return confirm('Confirmer?')">Supprimer</button>
</form>
```

## 7. Fragments (Réutilisation)

```html
<!-- Définir un fragment (header.html) -->
<div th:fragment="navbar">
    <nav class="navbar">
        <a th:href="@{/}">Accueil</a>
        <a th:href="@{/films}">Films</a>
    </nav>
</div>

<!-- Utiliser le fragment -->
<div th:replace="~{fragments/header :: navbar}"></div>
<!-- ou -->
<div th:insert="~{fragments/header :: navbar}"></div>

<!-- Fragment avec paramètres -->
<div th:fragment="card(titre, contenu)">
    <h3 th:text="${titre}"></h3>
    <p th:text="${contenu}"></p>
</div>

<!-- Utilisation -->
<div th:replace="~{fragments/card :: card('Titre', 'Contenu')}"></div>
```

## 8. Opérateurs

```html
<!-- Comparaison -->
th:if="${age > 18}"
th:if="${age >= 18}"
th:if="${age < 18}"
th:if="${age <= 18}"
th:if="${age == 18}"
th:if="${age != 18}"

<!-- Logique -->
th:if="${actif and disponible}"
th:if="${actif or disponible}"
th:if="${!actif}"

<!-- Null/Empty -->
th:if="${nom != null}"
th:if="${liste != null and !liste.isEmpty()}"
th:if="${#strings.isEmpty(nom)}"

<!-- Contient -->
th:if="${#strings.contains(nom, 'test')}"
th:if="${#lists.contains(liste, element)}"
```

## 9. Utilitaires

```html
<!-- Dates -->
<span th:text="${#dates.format(film.dateSortie, 'dd/MM/yyyy')}"></span>
<span th:text="${#dates.format(film.dateSortie, 'dd MMMM yyyy', 'fr')}"></span>
<span th:text="${#dates.day(date)}"></span>
<span th:text="${#dates.month(date)}"></span>
<span th:text="${#dates.year(date)}"></span>

<!-- Nombres -->
<span th:text="${#numbers.formatDecimal(prix, 1, 2)}"></span>
<span th:text="${#numbers.formatCurrency(prix)}"></span>

<!-- Chaînes -->
<span th:text="${#strings.toUpperCase(nom)}"></span>
<span th:text="${#strings.toLowerCase(nom)}"></span>
<span th:text="${#strings.substring(texte, 0, 10)}"></span>
<span th:text="${#strings.length(texte)}"></span>
<span th:text="${#strings.isEmpty(texte)}"></span>
<span th:text="${#strings.defaultString(texte, 'Par défaut')}"></span>

<!-- Listes -->
<span th:text="${#lists.size(films)}"></span>
<span th:text="${#lists.isEmpty(films)}"></span>

<!-- Objects -->
<span th:text="${#objects.nullSafe(film, 'titre')}"></span>
```

## 10. Variables locales

```html
<!-- Définir une variable -->
<div th:with="total=${film.prix * film.quantite}">
    <p th:text="'Total: ' + ${total}"></p>
</div>

<!-- Plusieurs variables -->
<div th:with="nom=${client.nom}, prenom=${client.prenom}">
    <p th:text="|${prenom} ${nom}|"></p>
</div>
```

## 11. Liens CSS/JS

```html
<!-- CSS -->
<link rel="stylesheet" th:href="@{/css/style.css}">
<link rel="stylesheet" th:href="@{/css/film/list.css}">

<!-- JavaScript -->
<script th:src="@{/js/app.js}"></script>

<!-- Avec versioning -->
<link rel="stylesheet" th:href="@{/css/style.css(v=${version})}">
```

## 12. Messages (i18n)

```html
<!-- Fichier messages.properties -->
<!-- welcome.message=Bienvenue {0} -->

<!-- Dans le template -->
<p th:text="#{welcome.message(${nom})}"></p>
<p th:text="#{label.titre}"></p>
```

## 13. Inline JavaScript

```html
<script th:inline="javascript">
    var films = /*[[${films}]]*/ [];
    var titre = /*[[${film.titre}]]*/ 'Titre par défaut';
    
    console.log('Films:', films);
</script>
```

## 14. Switch

```html
<div th:switch="${film.status}">
    <p th:case="'ACTIF'">Film actif</p>
    <p th:case="'INACTIF'">Film inactif</p>
    <p th:case="'ARCHIVE'">Film archivé</p>
    <p th:case="*">Statut inconnu</p>
</div>
```

## 15. Exemples pratiques

### Liste vide
```html
<tr th:if="${films == null or films.isEmpty()}">
    <td colspan="5">
        <div class="empty-state">
            <p>Aucun film trouvé</p>
        </div>
    </td>
</tr>
```

### Tableau dynamique
```html
<table>
    <thead>
        <tr>
            <th>#</th>
            <th>Titre</th>
            <th>Durée</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="film, stat : ${films}">
            <td th:text="${stat.count}"></td>
            <td th:text="${film.titre}"></td>
            <td th:text="${film.duree} + ' min'"></td>
            <td>
                <a th:href="@{/films/edit/{id}(id=${film.idFilm})}" class="btn">Modifier</a>
                <form th:action="@{/films/delete/{id}(id=${film.idFilm})}" method="post" style="display:inline;">
                    <button type="submit" class="btn-danger">Supprimer</button>
                </form>
            </td>
        </tr>
    </tbody>
</table>
```

### Formulaire complet
```html
<form th:action="@{${film != null} ? '/films/update' : '/films/save'}" 
      th:object="${film}" method="post">
    
    <input type="hidden" th:field="*{idFilm}" th:if="${film != null}">
    
    <div class="form-group">
        <label>Titre:</label>
        <input type="text" th:field="*{titre}" required>
    </div>
    
    <div class="form-group">
        <label>Langue:</label>
        <select th:field="*{idLangue}">
            <option value="">-- Sélectionner --</option>
            <option th:each="langue : ${langues}" 
                    th:value="${langue.idLangue}" 
                    th:text="${langue.nom}"
                    th:selected="${film != null and film.idLangue == langue.idLangue}">
            </option>
        </select>
    </div>
    
    <button type="submit" th:text="${film != null} ? 'Modifier' : 'Ajouter'"></button>
</form>
```

## 16. Bonnes pratiques

1. **Toujours utiliser `th:text` au lieu de `${...}` directement** pour éviter XSS
2. **Utiliser `@{...}` pour les URLs** pour gérer le context path automatiquement
3. **Préférer `th:field` dans les formulaires** pour lier automatiquement les champs
4. **Utiliser des fragments** pour réutiliser le code (navbar, footer, etc.)
5. **Ajouter des valeurs par défaut** dans le HTML pour la prévisualisation
6. **Utiliser `th:block`** pour regrouper sans créer d'élément DOM
7. **Activer le cache en production** : `spring.thymeleaf.cache=true`
8. **Désactiver le cache en développement** : `spring.thymeleaf.cache=false`

## 17. Configuration Spring Boot

```properties
# application.properties

# Désactiver le cache pour le développement
spring.thymeleaf.cache=false

# Préfixe et suffixe des templates
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Mode de template
spring.thymeleaf.mode=HTML

# Encoding
spring.thymeleaf.encoding=UTF-8
```
