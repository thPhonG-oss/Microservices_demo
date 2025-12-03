const express = require('express');
const router = express.Router();
const productController = require('../controllers/productController');
const { productValidationRules, validate, reduceStockRules } = require('../middleware/validation');

// Create product
router.post('/', productValidationRules(), validate, productController.createProduct);

// Get all products
router.get('/', productController.getAllProducts);

// Search products by name
router.get('/search', productController.searchProductsByName);

// Get products by category
router.get('/category/:category', productController.getProductsByCategory);

// Get product by ID
router.get('/:id', productController.getProductById);

// Update product
router.put('/:id', productValidationRules(), validate, productController.updateProduct);

// Delete product
router.delete('/:id', productController.deleteProduct);

// Reduce product stock
router.put('/:id/reduce-stock', reduceStockRules(), validate, productController.reduceStock);

module.exports = router;