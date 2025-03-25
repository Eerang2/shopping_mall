package shopping_mall.application.service.dto;

import shopping_mall.domain.product.model.Product;

public record ProductWithQuantity(Product product, int quantity) {
}
