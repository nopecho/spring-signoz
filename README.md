# SigNoz
OpenTelemetry 기반의 모니터링 백엔드 오픈소스 

## Required
* kubernetes version >= 1.22
* helm version >= 3.8

## install
1. signoz 헬름 저장소 추가 
```shell
helm repo add signoz https://charts.signoz.io
```
2. k8s signoz 워크로드용 네임스페이스 추가
```shell
kubectl create namespace platform
```
3. signoz 헬름 인스톨
```shell
helm -n platform install signoz-v1 signoz/signoz
```

## !
* 운영용 k8s 클러스터와 모니터링 k8s 클러스터를 구분하자

* local signoz 대시 보드 접근
  * http://localhost:3301/
```shell
export SIGNOZ_SERVICE_NAME=$(kubectl get svc -n platform -l "app.kubernetes.io/component=frontend" -o jsonpath="{.items[0].metadata.name}")
kubectl -n platform port-forward svc/$SIGNOZ_SERVICE_NAME 3301:3301
```

---

## OpenTelemetry Auto-Instrumentation (Kubernetes)

1. OpenTelemetry Operator 추가
```shell
kubectl apply -f https://github.com/open-telemetry/opentelemetry-operator/releases/latest/download/opentelemetry-operator.yaml
```

2. OpenTelemetry Collector 추가
```yaml
apiVersion: opentelemetry.io/v1alpha1
kind: OpenTelemetryCollector
metadata:
  name: signoz-otel-collector
spec:
  mode: sidecar
  config: |
    receivers:
      otlp:
        protocols:
          http:
          grpc:
    processors:
      batch:
    exporters:
      logging:
      otlp:
        endpoint: http://signoz-v1-otel-collector.platform.svc.cluster.local:4317
        tls:
          insecure: true
    service:
      pipelines:
        traces:
          receivers: [otlp]
          processors: [batch]
          exporters: [logging, otlp]
        metrics:
          receivers: [otlp]
          processors: [batch]
          exporters: [logging, otlp]
        logs:
          receivers: [otlp]
          processors: [batch]
          exporters: [logging, otlp]
```

3. OpenTelemetry Instrumentation 추가
```yaml
apiVersion: opentelemetry.io/v1alpha1
kind: Instrumentation
metadata:
  name: otel-instrumentation
spec:
  propagators:
    - tracecontext
    - baggage
    - b3
  sampler:
    type: parentbased_always_on
  java:
    image: ghcr.io/open-telemetry/opentelemetry-operator/autoinstrumentation-java:latest
  nodejs:
    image: ghcr.io/open-telemetry/opentelemetry-operator/autoinstrumentation-nodejs:latest
```
4. Pod sidecar 추가
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ your app }}
  labels:
    app: {{ your app }}
spec:
  replicas: 100
  selector:
    matchLabels:
      app: {{ your app }}
  template:
    metadata:
      name: {{ your app }}
      labels:
        app: {{ your app }}
      annotations:
        sidecar.opentelemetry.io/inject: "true"
        instrumentation.opentelemetry.io/inject-java: "true"
```