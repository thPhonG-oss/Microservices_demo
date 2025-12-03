const Eureka = require('eureka-js-client').Eureka;

const eurekaClient = new Eureka({
    instance: {
        app: process.env.SERVICE_NAME || 'product-service',
        instanceId: `${process.env.SERVICE_NAME || 'product-service'}:${process.env.SERVICE_PORT || 8081}`,
        hostName: 'product-service',
        ipAddr: 'product-service',
        statusPageUrl: `http://product-service:${process.env.SERVICE_PORT || 8081}/actuator/info`,
        healthCheckUrl: `http://product-service:${process.env.SERVICE_PORT || 8081}/actuator/health`,
        port: {
            '$': process.env.SERVICE_PORT || 8081,
            '@enabled': true,
        },
        vipAddress: process.env.SERVICE_NAME || 'product-service',
        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },
    },
    eureka: {
        host: process.env.EUREKA_HOST || 'localhost',
        port: process.env.EUREKA_PORT || 8761,
        servicePath: '/eureka/apps/',
        maxRetries: 3,
        requestRetryDelay: 2000,
    },
});

module.exports = eurekaClient;