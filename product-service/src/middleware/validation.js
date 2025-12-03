const { body, validationResult } = require('express-validator');

const productValidationRules = () => {
    return [
        body('name')
            .trim()
            .notEmpty().withMessage('Product name is required')
            .isLength({ min: 3, max: 100 }).withMessage('Product name must be between 3 and 100 characters'),
        
        body('description')
            .optional()
            .trim()
            .isLength({ max: 500 }).withMessage('Description cannot exceed 500 characters'),
        
        body('price')
            .notEmpty().withMessage('Price is required')
            .isFloat({ min: 0.01, max: 999999999.99 }).withMessage('Price must be between 0.01 and 999999999.99'),
        
        body('stock')
            .notEmpty().withMessage('Stock is required')
            .isInt({ min: 0, max: 999999 }).withMessage('Stock must be between 0 and 999999'),
        
        body('category')
            .trim()
            .notEmpty().withMessage('Category is required')
    ];
};

const validate = (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        const validationErrors = {};
        errors.array().forEach(error => {
            validationErrors[error.path] = error.msg;
        });

        return res.status(400).json({
            timestamp: new Date().toISOString(),
            status: 400,
            error: 'Validation failed',
            message: 'Invalid input data',
            validationErrors
        });
    }
    next();
};

const reduceStockRules = () => {
    return [
        body('quantity')
            .notEmpty().withMessage('Quantity is required')
            .isInt({ min: 1 }).withMessage('Quantity must be a positive integer')
    ];
};

module.exports = {
    productValidationRules,
    reduceStockRules,
    validate
};