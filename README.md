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