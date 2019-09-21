[![Build Status](https://travis-ci.com/bnc-projects/spring-boot-java-base.svg?branch=master)](https://travis-ci.com/bnc-projects/spring-boot-java-base)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=spring-boot-java-base&metric=alert_status)](https://sonarcloud.io/api/project_badges/measure?project=spring-boot-java-base&metric=alert_status)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=spring-boot-java-base&metric=coverage)](https://sonarcloud.io/api/project_badges/measure?project=spring-boot-java-base&metric=coverage)
[![Known Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=spring-boot-java-base&metric=vulnerabilities)](https://sonarcloud.io/api/project_badges/measure?project=spring-boot-java-base&metric=vulnerabilities)
# Spring Boot Java Base

## Build & Test

This project uses gradle and uses the default tasks to compile and run unit tests. 

```bash
./gradlew clean assemble check
```

### Build and run locally on Docker
1. Build the docker container
```bash
./gradlew clean assemble check docker
```
2. Run the docker container 
```bash
docker run -e SPRING_PROFILES_ACTIVE=localhost -p 8080:8080 -i -t spring-boot-java-base
```

### Build production equivalent container
```bash
./gradlew clean assemble check docker dockerTag -PTAG=$(git rev-parse --verify HEAD --short) -PREPOSITORY_URI=${DOCKER_REPO}${IMAGE_NAME}
```

### Profiling
To debug the container locally, the `JAVA_OPTS` environment variable can be provided when running the container.
```bash
docker run -p 8080:8080 -i -t -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" spring-boot-java-base
```

### Enabling New Relic
New relic can be enabled by providing the following `JAVA_OPTS` environment variable. Ensure to provide the correct `newrelic.environment` system property. To configure New Relic add/update the Application Environments section in `newrelic.yml`, more information can be found [here](https://docs.newrelic.com/docs/agents/java-agent/configuration/java-agent-configuration-config-file). 
```bash
docker run -p 8080:8080 -i -t -e JAVA_OPTS="-javaagent:newrelic/newrelic.jar -Dnewrelic.environment=development -Dnewrelic.config.file=newrelic/newrelic.yml" spring-boot-java-base
```

## Deployment

This project uses Terraform and the AWS CLI to deploy the service to the BNC ECS Cluster. To have the CI/CD pipeline deploy a service which has be deployed using a fork of this project you can follow the instructions below.

### Terraform ECS Workspaces

By default Terraform will not create the required workspaces. Before setting up the deployment in the CI environment, ensure you have created all of the appropriate workspaces.

The default workspaces for BNC are:
* development
* production

To create the workspaces run the following commands:
```
cd deployment/terraform/ecs-service
terraform workspace new production
terraform workspace new development
```

### Setting up Travis-CI deployment

1. Encrypt the following global environment variables using the Travis-CI CLI.
```
DEPLOYMENT_ACCESS_KEY_ID=
DEPLOYMENT_SECRET_ACCESS_KEY=
AWS_DEFAULT_REGION=
KMS_KEY_ID=
ROLE_ARN=
STATE_S3_BUCKET=
STATE_DYNAMODB_TABLE=
KEY=<The project key for the ECR repository>, e.g bnc/<team>/ecr/<service-name>
SERVICE_KEY=<The project key for ECS service>, e.g ecs/<service-name>
SPLUNK_URL=
OPERATIONS_ROLE_ARN=
```

2. Encrypt the following environment variables for the development deployment:
```
TF_WORKSPACE=
SPLUNK_TOKEN=
```

### Deployment to development ECS cluster

#### Terraform ECR Project

1. cd deployment/terraform/ecr

2. Copy `backend.tfvars.example` to `backend.tfvars`.

3. Fill out the `backend.tfvars`

4. Run `terraform init "-backend-config=backend.tfvars"`.

5. Copy `master.tfvars.example` to `master.tfvars`.

6. Fill in the `master.tfvars` with the correct values.

7. Now the project is fully setup and you will have the ability to run [terraform commands](https://www.terraform.io/docs/commands/index.html).
```
terraform plan "-var-file=master.tfvars"
```

#### Terraform ECS Project

1. cd deployment/terraform/ecs-service

2. Copy `backend.tfvars.example` to `backend.tfvars`.

3. Fill out the `backend.tfvars`

4. Run `terraform init "-backend-config=backend.tfvars"`.

5. Copy `master.tfvars.example` to `master.tfvars`.

6. Fill in the `master.tfvars` with the correct values.

7. Select the development work space `terraform workspace select development`

8. Now the project is fully setup and you will have the ability to run [terraform commands](https://www.terraform.io/docs/commands/index.html).
```
terraform plan "-var-file=master.tfvars"
```
