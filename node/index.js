import {otelTracing} from './tracing.js';
otelTracing(process.env.OTLP_ENDPOINT, "app-name");