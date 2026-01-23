// Formatage des nombres avec séparateurs de milliers
document.addEventListener('DOMContentLoaded', function() {
    // Fonction pour formater un nombre avec séparateurs de milliers
    function formatNumber(num) {
        if (num === null || num === undefined || num === '' || num === '-') {
            return num;
        }
        
        // Convertir en nombre
        let number = parseFloat(num.toString().replace(/[^\d.-]/g, ''));
        
        if (isNaN(number)) {
            return num;
        }
        
        // Formater avec séparateurs de milliers (espace) et 2 décimales si nécessaire
        let parts = number.toFixed(2).split('.');
        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
        
        // Si les décimales sont .00, on les enlève
        if (parts[1] === '00') {
            return parts[0];
        }
        
        return parts.join(',');
    }
    
    // Sélectionner toutes les cellules de tableau et spans qui contiennent des valeurs monétaires
    function formatMonetaryValues() {
        // Patterns pour identifier les valeurs monétaires
        const monetaryPatterns = [
            /^\d+([.,]\d+)?$/,           // Nombre simple: 1234 ou 1234.56
            /^\d+([.,]\d+)?\s*(€|Ar|ar|EUR)?$/i,  // Nombre avec devise
            /^-?\d+([.,]\d+)?$/          // Nombre négatif
        ];
        
        // Parcourir toutes les cellules de tableau
        document.querySelectorAll('td').forEach(function(cell) {
            let text = cell.textContent.trim();
            
            // Vérifier si c'est une valeur monétaire
            for (let pattern of monetaryPatterns) {
                if (pattern.test(text)) {
                    // Extraire le nombre et la devise
                    let match = text.match(/^(-?\d+(?:[.,]\d+)?)\s*(€|Ar|ar|EUR)?$/i);
                    if (match) {
                        let number = match[1].replace(',', '.');
                        let currency = match[2] || '';
                        let formatted = formatNumber(number);
                        cell.textContent = currency ? formatted + ' ' + currency : formatted;
                    }
                    break;
                }
            }
        });
        
        // Parcourir les spans avec classe value ou montant
        document.querySelectorAll('.value, .detail-montant, .place-price, [class*="montant"], [class*="prix"], [class*="total"]').forEach(function(elem) {
            let text = elem.textContent.trim();
            let match = text.match(/^(-?\d+(?:[.,]\d+)?)\s*(€|Ar|ar|EUR)?$/i);
            if (match) {
                let number = match[1].replace(',', '.');
                let currency = match[2] || '';
                let formatted = formatNumber(number);
                elem.textContent = currency ? formatted + ' ' + currency : formatted;
            }
        });
    }
    
    // Exécuter le formatage
    formatMonetaryValues();
});
