package ke.natujenge.whatsappbot.service;

import ke.natujenge.whatsappbot.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CatalogService {

    public List<Product> getCatalog() {
        List<Product> products = new ArrayList<>();
        Product product = new Product(1, "Emerald Blue Ceramic Tiles", 1151.0);
        Product product1 = new Product(2, "Rustico Ash Ceramic Tiles", 1295.0);
        Product product2 = new Product(3, "Marmo Antico Ceramic Wall Tiles", 1243.50);
        Product product3 = new Product(4, "Marmola Ice Ceramic", 1741.82);

        products.add(product);
        products.add(product1);
        products.add(product2);
        products.add(product3);

        return products;
    }
}
