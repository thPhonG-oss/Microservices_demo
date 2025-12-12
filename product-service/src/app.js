require('dotenv').config();
const express = require('express');
const connectDB = require('./config/database');
const eurekaClient = require('./config/eureka');
const productRoutes = require('./routes/productRoutes');
const errorHandler = require('./middleware/errorHandler');

// Zipkin imports
const { tracer } = require('./config/zipkin');
const { expressMiddleware } = require('zipkin-instrumentation-express');

const app = express();
const PORT = process.env.SERVICE_PORT || 8081;

// Zipkin middleware - PHẢI ĐẶT TRƯỚC TẤT CẢ MIDDLEWARE KHÁC
app.use(expressMiddleware({ tracer }));

// Regular Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Health check endpoints (for Eureka)
app.get('/actuator/health', (req, res) => {
    res.status(200).json({
        status: 'UP',
        components: {
            mongodb: {
                status: 'UP'
            },
            zipkin: {
                status: 'UP',
                endpoint: process.env.ZIPKIN_URL || 'http://localhost:9411'
            }
        }
    });
});

app.get('/actuator/info', (req, res) => {
    res.status(200).json({
        app: {
            name: 'product-service-nodejs',
            version: '1.0.0',
            description: 'Product Service using Node.js and Express',
            tracing: 'Zipkin enabled'
        }
    });
});

// Routes
app.use('/api/products', productRoutes);

// Error handling middleware (must be last)
app.use(errorHandler);

// Connect to MongoDB and start server
const startServer = async () => {
    try {
        // Connect to MongoDB
        await connectDB();
        
        // Start Express server
        app.listen(PORT, () => {
            console.log('╔════════════════════════════════════════════════════════════════════════════╗');
            console.log('║    Product Service (Node.js) Started Successfully!                        ║');
            console.log('║                                                                            ║');
            console.log(`║   Service URL: http://localhost:${PORT}                                    ║`);
            console.log(`║   API Base: http://localhost:${PORT}/api/products                          ║`);
            console.log('║   Database: MongoDB (productdb)                                            ║');
            console.log(`║   Eureka: http://${process.env.EUREKA_HOST}:${process.env.EUREKA_PORT}     ║`);
            console.log(`║   Zipkin: ${process.env.ZIPKIN_URL || 'http://localhost:9411'}             ║`);
            console.log('║                                                                            ║');
            console.log(`║   Health Check: http://localhost:${PORT}/actuator/health                   ║`);
            console.log(`║   Zipkin UI: http://localhost:9411                                         ║`);
            console.log('╚════════════════════════════════════════════════════════════════════════════╝');
        });
        
        // Register with Eureka
        eurekaClient.start((error) => {
            if (error) {
                console.error('❌ Eureka registration failed:', error);
            } else {
                console.log('✅ Successfully registered with Eureka Server');
            }
        });
        
    } catch (error) {
        console.error('❌ Failed to start server:', error);
        process.exit(1);
    }
};

// Handle shutdown gracefully
process.on('SIGINT', () => {
    console.log('\n🛑 Shutting down gracefully...');
    eurekaClient.stop(() => {
        console.log('✅ Deregistered from Eureka');
        process.exit(0);
    });
});

startServer();