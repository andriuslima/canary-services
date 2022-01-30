# Canary Api

### Create cloud build trigger
```
gcloud beta builds triggers create github --trigger-config cicd/build-trigger.yaml
```

```
gcloud beta builds triggers create github --trigger-config cicd/deploy-trigger.yaml
```