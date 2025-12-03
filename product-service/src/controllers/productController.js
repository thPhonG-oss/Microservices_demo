// src/controllers/productController.js
const productService = require('../services/productService');

// Create a new product
exports.createProduct = async (req, res, next) => {
    try {
        console.log('📝 Creating product:', req.body.name);
        const product = await productService.createProduct(req.body);
        console.log('✅ Product created successfully');
        res.status(201).json(product);
    } catch (error) {
        next(error);
    }
};

// Get all products
exports.getAllProducts = async (req, res, next) => {
    try {
        console.log('📋 Retrieving all products');
        const products = await productService.getAllProducts();
        res.status(200).json(products);
    } catch (error) {
        next(error);
    }
};

// Get product by ID
exports.getProductById = async (req, res, next) => {
    try {
        const { id } = req.params;
        console.log('🔍 Retrieving product with ID:', id);
        const product = await productService.getProductById(id);
        res.status(200).json(product);
    } catch (error) {
        next(error);
    }
};

// Update product
exports.updateProduct = async (req, res, next) => {
    try {
        const { id } = req.params;
        console.log('✏️ Updating product with ID:', id);
        const product = await productService.updateProduct(id, req.body);
        console.log('✅ Product updated successfully');
        res.status(200).json(product);
    } catch (error) {
        next(error);
    }
};

// Delete product
exports.deleteProduct = async (req, res, next) => {
    try {
        const { id } = req.params;
        console.log('🗑️ Deleting product with ID:', id);
        await productService.deleteProduct(id);
        console.log('✅ Product deleted successfully');
        res.status(204).send();
    } catch (error) {
        next(error);
    }
};

// Get products by category
exports.getProductsByCategory = async (req, res, next) => {
    try {
        const { category } = req.params;
        console.log('📂 Retrieving products by category:', category);
        const products = await productService.getProductsByCategory(category);
        res.status(200).json(products);
    } catch (error) {
        next(error);
    }
};

// Search products by name
exports.searchProductsByName = async (req, res, next) => {
    try {
        const { name } = req.query;
        console.log('🔎 Searching products by name:', name);
        const products = await productService.searchProductsByName(name);
        res.status(200).json(products);
    } catch (error) {
        next(error);
    }
};

exports.reduceStock = async (req, res, next) => {
    try {
        const { id } = req.params;
        const { quantity } = req.body;

        console.log(`📉 Reducing stock for product ${id} by ${quantity}`);

        const product = await productService.reduceStock(id, quantity);
        
        console.log(`✅ Stock reduced successfully. New stock: ${product.stock}`);
        
        res.status(200).json(product);
    } catch (error) {
        next(error);
    }
};