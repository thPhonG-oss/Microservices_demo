// src/services/productService.js
const Product = require('../models/Product');

class ProductService {
    
    // Helper function để format response (DTO)
    formatProductResponse(product) {
        return {
            id: product._id,
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            category: product.category,
            createdAt: product.createdAt,
            updatedAt: product.updatedAt
        };
    }

    async createProduct(productData) {
        const product = new Product(productData);
        const savedProduct = await product.save();
        return this.formatProductResponse(savedProduct);
    }

    async getAllProducts() {
        const products = await Product.find();
        return products.map(product => this.formatProductResponse(product));
    }

    async getProductById(id) {
        const product = await Product.findById(id);
        if (!product) {
            const error = new Error(`Product with ID: ${id} not found`);
            error.status = 404;
            throw error;
        }
        return this.formatProductResponse(product);
    }

    async updateProduct(id, updateData) {
        const product = await Product.findByIdAndUpdate(
            id,
            { ...updateData, updatedAt: Date.now() },
            { new: true, runValidators: true }
        );

        if (!product) {
            const error = new Error(`Product with ID: ${id} not found`);
            error.status = 404;
            throw error;
        }
        return this.formatProductResponse(product);
    }

    async deleteProduct(id) {
        const product = await Product.findByIdAndDelete(id);
        if (!product) {
            const error = new Error(`Product with ID: ${id} not found`);
            error.status = 404;
            throw error;
        }
        return true;
    }

    async getProductsByCategory(category) {
        const products = await Product.find({ category });
        return products.map(product => this.formatProductResponse(product));
    }

    async searchProductsByName(name) {
        const products = await Product.find({ 
            name: { $regex: name, $options: 'i' } 
        });
        return products.map(product => this.formatProductResponse(product));
    }

    async reduceStock(id, quantity) {
        // Atomic operation: Tìm sản phẩm có ID này VÀ stock >= quantity
        // Sau đó trừ stock đi quantity
        const product = await Product.findOneAndUpdate(
            { 
                _id: id, 
                stock: { $gte: quantity } // Điều kiện: Tồn kho phải đủ
            },
            { 
                $inc: { stock: -quantity }, // Trừ số lượng (Atomic)
                updatedAt: Date.now() 
            },
            { new: true } // Trả về dữ liệu sau khi update
        );

        if (!product) {
            // Nếu không trả về product, có nghĩa là:
            // 1. Không tìm thấy ID
            // 2. Hoặc stock < quantity
            
            // Để báo lỗi chính xác hơn, ta có thể kiểm tra xem sản phẩm có tồn tại không
            const exists = await Product.findById(id);
            if (!exists) {
                const error = new Error(`Product with ID: ${id} not found`);
                error.status = 404;
                throw error;
            } else {
                const error = new Error(`Insufficient stock for product ID: ${id}`);
                error.status = 400; // Bad Request
                throw error;
            }
        }

        return this.formatProductResponse(product);
    }
}

module.exports = new ProductService();