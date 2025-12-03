const Product = require('../models/Product');

// Create a new product
exports.createProduct = async (req, res, next) => {
    try {
        console.log('📝 Creating product:', req.body.name);
        
        const product = new Product(req.body);
        const savedProduct = await product.save();
        
        console.log('✅ Product created successfully with ID:', savedProduct._id);
        
        res.status(201).json({
            id: savedProduct._id,
            name: savedProduct.name,
            description: savedProduct.description,
            price: savedProduct.price,
            stock: savedProduct.stock,
            category: savedProduct.category,
            createdAt: savedProduct.createdAt,
            updatedAt: savedProduct.updatedAt
        });
    } catch (error) {
        next(error);
    }
};

// Get all products
exports.getAllProducts = async (req, res, next) => {
    try {
        console.log('📋 Retrieving all products');
        
        const products = await Product.find();
        
        const response = products.map(product => ({
            id: product._id,
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            category: product.category,
            createdAt: product.createdAt,
            updatedAt: product.updatedAt
        }));
        
        res.status(200).json(response);
    } catch (error) {
        next(error);
    }
};

// Get product by ID
exports.getProductById = async (req, res, next) => {
    try {
        const { id } = req.params;
        console.log('🔍 Retrieving product with ID:', id);
        
        const product = await Product.findById(id);
        
        if (!product) {
            const error = new Error(`Product with ID: ${id} not found`);
            error.status = 404;
            throw error;
        }
        
        res.status(200).json({
            id: product._id,
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            category: product.category,
            createdAt: product.createdAt,
            updatedAt: product.updatedAt
        });
    } catch (error) {
        next(error);
    }
};

// Update product
exports.updateProduct = async (req, res, next) => {
    try {
        const { id } = req.params;
        console.log('✏️ Updating product with ID:', id);
        
        const product = await Product.findByIdAndUpdate(
            id,
            { ...req.body, updatedAt: Date.now() },
            { new: true, runValidators: true }
        );
        
        if (!product) {
            const error = new Error(`Product with ID: ${id} not found`);
            error.status = 404;
            throw error;
        }
        
        console.log('✅ Product updated successfully with ID:', product._id);
        
        res.status(200).json({
            id: product._id,
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            category: product.category,
            createdAt: product.createdAt,
            updatedAt: product.updatedAt
        });
    } catch (error) {
        next(error);
    }
};

// Delete product
exports.deleteProduct = async (req, res, next) => {
    try {
        const { id } = req.params;
        console.log('🗑️ Deleting product with ID:', id);
        
        const product = await Product.findByIdAndDelete(id);
        
        if (!product) {
            const error = new Error(`Product with ID: ${id} not found`);
            error.status = 404;
            throw error;
        }
        
        console.log('✅ Product deleted successfully with ID:', id);
        
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
        
        const products = await Product.find({ category });
        
        const response = products.map(product => ({
            id: product._id,
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            category: product.category,
            createdAt: product.createdAt,
            updatedAt: product.updatedAt
        }));
        
        res.status(200).json(response);
    } catch (error) {
        next(error);
    }
};

// Search products by name
exports.searchProductsByName = async (req, res, next) => {
    try {
        const { name } = req.query;
        console.log('🔎 Searching products by name:', name);
        
        const products = await Product.find({ 
            name: { $regex: name, $options: 'i' } 
        });
        
        const response = products.map(product => ({
            id: product._id,
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            category: product.category,
            createdAt: product.createdAt,
            updatedAt: product.updatedAt
        }));
        
        res.status(200).json(response);
    } catch (error) {
        next(error);
    }
};