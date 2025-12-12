// src/config/tracing.js
const { NodeSDK } = require('@opentelemetry/sdk-node');
const { ZipkinExporter } = require('@opentelemetry/exporter-zipkin');
const { getNodeAutoInstrumentations } = require('@opentelemetry/auto-instrumentations-node');
// Sửa dòng này: Thay vì import Resource class, ta dùng resourceFromAttributes
const { resourceFromAttributes } = require('@opentelemetry/resources');
const { 
  ATTR_SERVICE_NAME 
} = require('@opentelemetry/semantic-conventions');

console.log('⏳ Initializing OpenTelemetry...');

// Cấu hình Zipkin Exporter
const zipkinExporter = new ZipkinExporter({
  url: process.env.ZIPKIN_URL 
    ? `${process.env.ZIPKIN_URL}/api/v2/spans` 
    : 'http://localhost:9411/api/v2/spans',
  serviceName: process.env.SERVICE_NAME || 'product-service',
});

// Khởi tạo OpenTelemetry SDK
const sdk = new NodeSDK({
  // SỬA Ở ĐÂY: Dùng resourceFromAttributes thay vì new Resource
  resource: resourceFromAttributes({
    [ATTR_SERVICE_NAME]: process.env.SERVICE_NAME || 'product-service',
  }),
  
  traceExporter: zipkinExporter,
  instrumentations: [getNodeAutoInstrumentations()],
});

// Start SDK
try {
    sdk.start();
    console.log('✅ OpenTelemetry initialized with Zipkin exporter');
} catch (error) {
    console.error('❌ Error initializing OpenTelemetry:', error);
}

// Graceful shutdown
process.on('SIGTERM', () => {
  sdk.shutdown()
    .then(() => console.log('Tracing terminated'))
    .catch((error) => console.log('Error terminating tracing', error))
    .finally(() => process.exit(0));
});