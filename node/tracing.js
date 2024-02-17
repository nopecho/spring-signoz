'use strict';
const process = require('process');
const opentelemetry = require('@opentelemetry/sdk-node');
const { getNodeAutoInstrumentations } = require('@opentelemetry/auto-instrumentations-node');
const { OTLPTraceExporter } = require('@opentelemetry/exporter-trace-otlp-http');
const { Resource } = require('@opentelemetry/resources');
const { SemanticResourceAttributes } = require('@opentelemetry/semantic-conventions');

const otelTracing = (otlpEndpoint, serviceName) => {
    const exporterOptions = {
        url: `${otlpEndpoint}`
    }

    const traceExporter = new OTLPTraceExporter(exporterOptions);
    const sdk = new opentelemetry.NodeSDK({
        traceExporter,
        instrumentations: [getNodeAutoInstrumentations()],
        resource: new Resource({
            [SemanticResourceAttributes.SERVICE_NAME]: serviceName
        })
    });

    sdk.start()

    process.on('SIGTERM', () => {
        sdk.shutdown()
            .then(() => console.log('Tracing terminated'))
            .catch((e) => console.log('Error terminating tracing', e))
            .finally(() => process.exit(0));
    });
}

module.exports = { otelTracing };