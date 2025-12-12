const { Tracer, BatchRecorder, jsonEncoder } = require('zipkin');
const { HttpLogger } = require('zipkin-transport-http');
const CLSContext = require('zipkin-context-cls');

const zipkinUrl = process.env.ZIPKIN_URL || 'http://localhost:9411';

// Create a CLS context (Continuation-Local Storage)
const ctxImpl = new CLSContext('zipkin');

// Create a recorder that sends traces to Zipkin
const recorder = new BatchRecorder({
    logger: new HttpLogger({
        endpoint: `${zipkinUrl}/api/v2/spans`,
        jsonEncoder: jsonEncoder.JSON_V2
    })
});

// Create the tracer
const tracer = new Tracer({
    ctxImpl,
    recorder,
    localServiceName: process.env.SERVICE_NAME || 'product-service',
    supportsJoin: false // Set to true if you want to support span joining
});

console.log(`✅ Zipkin tracer configured: ${zipkinUrl}`);

module.exports = { tracer, ctxImpl };