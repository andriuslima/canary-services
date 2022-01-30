### Create cloud build
```
gcloud builds submit --config cicd/build.yaml
```

### Create cloud build trigger
```
gcloud beta builds triggers create github --trigger-config cicd/build-trigger.yaml
```
