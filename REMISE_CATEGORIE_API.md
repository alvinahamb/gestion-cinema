# 🎬 API Documentation - Remise Catégorie

## Endpoints REST

### 📋 Récupérer toutes les remises
```http
GET /remise-categories
Response: List<RemiseCategorieDTO>
```

### ➕ Formulaire d'ajout
```http
GET /remise-categories/nouveau
Response: HTML Form avec:
  - typePlaces: List<TypePlaceDTO>
  - categorieClients: List<CategorieClientDTO>
```

### ✏️ Formulaire de modification
```http
GET /remise-categories/edit/{id}
Params:
  - id: Integer (idRemise)
Response: HTML Form pré-rempli
```

### 💾 Créer une remise
```http
POST /remise-categories/save
Content-Type: application/x-www-form-urlencoded

Body:
{
  "idTypePlace": 1,
  "idCategorieClient": 2,
  "tarif": 15.50,
  "pourcentageDeReduction": 10.00
}
Response: Redirect to /remise-categories
```

### 🔄 Mettre à jour une remise
```http
POST /remise-categories/update?id=1
Content-Type: application/x-www-form-urlencoded

Body:
{
  "idRemise": 1,
  "idTypePlace": 1,
  "idCategorieClient": 2,
  "tarif": 20.00,
  "pourcentageDeReduction": 15.00
}
Response: Redirect to /remise-categories
```

### 🗑️ Supprimer une remise
```http
POST /remise-categories/delete/{id}
Params:
  - id: Integer (idRemise)
Response: Redirect to /remise-categories
```

---

## Service Methods

### RemiseCategorieService

#### getAllRemiseCategories()
```java
public List<RemiseCategorieDTO> getAllRemiseCategories()
// Returns all remises from database
```

#### getRemiseCategorieById(Integer id)
```java
public Optional<RemiseCategorieDTO> getRemiseCategorieById(Integer id)
// Returns specific remise by ID
```

#### getRemisesByTypePlace(Integer idTypePlace)
```java
public List<RemiseCategorieDTO> getRemisesByTypePlace(Integer idTypePlace)
// Returns all remises for a specific type of place
// Useful for calculating prices in checkout
```

#### getRemisesByCategorie(Integer idCategorieClient)
```java
public List<RemiseCategorieDTO> getRemisesByCategorie(Integer idCategorieClient)
// Returns all remises for a specific client category
// Useful for showing client's available discounts
```

#### createRemiseCategorie(RemiseCategorieDTO remiseCategorieDTO)
```java
public RemiseCategorieDTO createRemiseCategorie(RemiseCategorieDTO remiseCategorieDTO)
// Creates new remise
// Returns created RemiseCategorieDTO
```

#### updateRemiseCategorie(Integer id, RemiseCategorieDTO remiseCategorieDTO)
```java
public RemiseCategorieDTO updateRemiseCategorie(Integer id, RemiseCategorieDTO remiseCategorieDTO)
// Updates existing remise
// Only updates tarif and pourcentageDeReduction (type and category are keys)
```

#### deleteRemiseCategorie(Integer id)
```java
public void deleteRemiseCategorie(Integer id)
// Deletes remise by ID
```

---

## Data Transfer Object (DTO)

### RemiseCategorieDTO
```java
public class RemiseCategorieDTO {
    private Integer idRemise;
    private Integer idTypePlace;
    private Integer idCategorieClient;
    private BigDecimal tarif;
    private BigDecimal pourcentageDeReduction;
    // getters, setters, constructors...
}
```

---

## Repository Queries

### RemiseCategorieRepository Methods

#### findByTypePlaceIdTypePlaceAndCategorieCategoryIdCategorieClient()
```java
Optional<RemiseCategorie> findByTypePlaceIdTypePlaceAndCategorieCategoryIdCategorieClient(
    Integer idTypePlace, 
    Integer idCategorieClient
)
// Find exact discount for specific type and category
```

#### findByTypePlaceIdTypePlace()
```java
List<RemiseCategorie> findByTypePlaceIdTypePlace(Integer idTypePlace)
// Find all discounts for a type of place
```

#### findByCategorieCategoryIdCategorieClient()
```java
List<RemiseCategorie> findByCategorieCategoryIdCategorieClient(Integer idCategorieClient)
// Find all discounts for a client category
```

---

## Usage Examples

### Example 1: Get all discounts
```java
@Autowired
private RemiseCategorieService remiseCategorieService;

public void getAllDiscounts() {
    List<RemiseCategorieDTO> remises = remiseCategorieService.getAllRemiseCategories();
    remises.forEach(r -> System.out.println(r.getPourcentageDeReduction() + "%"));
}
```

### Example 2: Get discounts for a specific client
```java
public void getClientDiscounts(Integer clientCategoryId) {
    List<RemiseCategorieDTO> remises = 
        remiseCategorieService.getRemisesByCategorie(clientCategoryId);
    
    remises.forEach(r -> {
        System.out.println("Type Place: " + r.getIdTypePlace());
        System.out.println("Discount: " + r.getPourcentageDeReduction() + "%");
        System.out.println("Price: " + r.getTarif());
    });
}
```

### Example 3: Create a student discount
```java
public void createStudentDiscount() {
    RemiseCategorieDTO discount = new RemiseCategorieDTO();
    discount.setIdTypePlace(2);              // Normal seats
    discount.setIdCategorieClient(1);        // Students
    discount.setTarif(10.00);
    discount.setPourcentageDeReduction(20.00); // 20% off
    
    RemiseCategorieDTO created = 
        remiseCategorieService.createRemiseCategorie(discount);
    
    System.out.println("Discount created: " + created.getIdRemise());
}
```

### Example 4: Calculate final price
```java
public BigDecimal calculatePrice(Integer typePlace, Integer clientCategory, BigDecimal baseTarif) {
    List<RemiseCategorieDTO> remises = 
        remiseCategorieService.getRemisesByTypePlace(typePlace);
    
    RemiseCategorieDTO applicable = remises.stream()
        .filter(r -> r.getIdCategorieClient().equals(clientCategory))
        .findFirst()
        .orElse(null);
    
    if (applicable != null) {
        BigDecimal discount = baseTarif
            .multiply(applicable.getPourcentageDeReduction())
            .divide(new BigDecimal(100));
        return baseTarif.subtract(discount);
    }
    
    return baseTarif;
}
```

---

## HTML Forms

### Add/Edit Remise Form
```html
<form action="/remise-categories/save" method="post">
    <select name="idTypePlace" required>
        <option th:each="tp : ${typePlaces}" 
                th:value="${tp.idTypePlace}" 
                th:text="${tp.libelle}">
    </select>
    
    <select name="idCategorieClient" required>
        <option th:each="cc : ${categorieClients}" 
                th:value="${cc.idCategorieClient}" 
                th:text="${cc.libelle}">
    </select>
    
    <input type="number" step="0.01" name="tarif" placeholder="Price"/>
    <input type="number" step="0.01" name="pourcentageDeReduction" placeholder="% Discount"/>
    
    <button type="submit">Save</button>
</form>
```

---

## Error Handling

### Possible Errors

| Error | Cause | Solution |
|-------|-------|----------|
| `UNIQUE violation` | Duplicate (typePlace, category) | Delete existing or update instead |
| `Foreign Key violation` | Invalid typePlace ID | Ensure ID exists in type_place table |
| `Foreign Key violation` | Invalid category ID | Ensure ID exists in categorie_client table |
| `NullPointerException` | Type place or category not found | Check repository query result |

---

## Testing

### Unit Test Example
```java
@Test
public void testCreateRemise() {
    RemiseCategorieDTO dto = new RemiseCategorieDTO();
    dto.setIdTypePlace(1);
    dto.setIdCategorieClient(1);
    dto.setTarif(new BigDecimal("15.50"));
    dto.setPourcentageDeReduction(new BigDecimal("20.00"));
    
    RemiseCategorieDTO created = service.createRemiseCategorie(dto);
    
    assertNotNull(created.getIdRemise());
    assertEquals(1, created.getIdTypePlace().intValue());
}
```

---

## Performance Notes

- **N+1 Query Problem**: Consider using `@EntityGraph` for optimizations
- **Caching**: Discounts are frequently accessed, consider caching them
- **Indexing**: Add indexes on `id_type_place` and `id_categorie_client` for faster queries
