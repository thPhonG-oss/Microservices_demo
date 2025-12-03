const errorHandler = (err, req, res, next) => {
    console.error('❌ Error:', err);

    // Mongoose validation error
    if (err.name === 'ValidationError') {
        const validationErrors = {};
        Object.keys(err.errors).forEach(key => {
            validationErrors[key] = err.errors[key].message;
        });

        return res.status(400).json({
            timestamp: new Date().toISOString(),
            status: 400,
            error: 'Validation Error',
            message: 'Invalid input data',
            validationErrors
        });
    }

    // Mongoose CastError (invalid ID format)
    if (err.name === 'CastError') {
        return res.status(400).json({
            timestamp: new Date().toISOString(),
            status: 400,
            error: 'Bad Request',
            message: 'Invalid ID format'
        });
    }

    // Product not found
    if (err.message && err.message.includes('not found')) {
        return res.status(404).json({
            timestamp: new Date().toISOString(),
            status: 404,
            error: 'NOT FOUND',
            message: err.message
        });
    }

    // Default error
    res.status(err.status || 500).json({
        timestamp: new Date().toISOString(),
        status: err.status || 500,
        error: err.name || 'Internal Server Error',
        message: err.message || 'Something went wrong'
    });
};

module.exports = errorHandler;