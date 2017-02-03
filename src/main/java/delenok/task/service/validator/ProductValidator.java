package delenok.task.service.validator;

import delenok.task.domain.Product;
import delenok.task.exceptions.NotExistException;

public class ProductValidator {
    public static void productExist(Product product) {
        if (product == null) {
            throw new NotExistException("Product not exists");
        }
    }
}
