CLUSTER_NAME=migration-onboarding
MIGRATION_ONBOARDING_VERSION=0.0.1
POSTGRES_IMAGE=postgres:14.0
PROMETHEUS_IMAGE=prom/prometheus
GRAFANA_IMAGE=grafana/grafana:latest
LOCALSTACK_IMAGE=localstack/localstack:0.14.2
FLYWAY_IMAGE=flyway/flyway:8.5.4

clean:
		kind delete cluster --name=${CLUSTER_NAME}

clean-namespace:
		kubectl delete namespace ${CLUSTER_NAME}

cluster:
		kind create cluster --config=k8s/cluster-config.yaml --name=${CLUSTER_NAME}

		# NOTE: currently this needs to be run twice to work correctly. Not sure why
loadImage:
		kind load docker-image ${POSTGRES_IMAGE} --name=${CLUSTER_NAME}
		kind load docker-image  ${PROMETHEUS_IMAGE} --name=${CLUSTER_NAME}
		kind load docker-image ${GRAFANA_IMAGE} --name=${CLUSTER_NAME}
		kind load docker-image ${LOCALSTACK_IMAGE} --name=${CLUSTER_NAME}
		kind load docker-image ${FLYWAY_IMAGE} --name=${CLUSTER_NAME}

# NOTE: currently this needs to be run twice to work correctly. Not sure why
dependencies:
		kubectl apply -k k8s/overlays/local-dev -n ${CLUSTER_NAME}

# The buckets need to exist - required localstack dependency running
buckets:
		kubectl -n ${CLUSTER_NAME} exec deploy/localstack -- aws --endpoint-url=http://localhost:4566 s3 mb s3://wd-ue1-d3-boss-core
		kubectl -n ${CLUSTER_NAME} exec deploy/localstack -- aws --endpoint-url=http://localhost:4566 s3 mb s3://boss-core-file-services
		kubectl -n ${CLUSTER_NAME} exec deploy/localstack -- aws --endpoint-url=http://localhost:4566 s3api list-buckets

# This is a time consuming operation. Builds all components, creates docker images and loads images into Kind cluster
publish:
		#sbt Docker/publishLocal
		#kind load docker-image boss-gateway:${BOSS_VERSION} --name=${CLUSTER_NAME}
		#kind load docker-image boss-core:${BOSS_VERSION} --name=${CLUSTER_NAME}
		#kind load docker-image boss-api:${BOSS_VERSION} --name=${CLUSTER_NAME}
		#kind load docker-image boss-fs-api:${BOSS_VERSION} --name=${CLUSTER_NAME}
		#kind load docker-image boss-fs-dummy:${BOSS_VERSION} --name=${CLUSTER_NAME}
		#kind load docker-image boss-analytics:${BOSS_VERSION} --name=${CLUSTER_NAME}
		#kind load docker-image boss-streaming-migration:${BOSS_VERSION} --name=${CLUSTER_NAME}


apply:
		#kubectl apply -k k8s/boss-gateway/environments/local-dev -n boss
		#kubectl apply -k k8s/boss-core/environments/local-dev -n boss
		#kubectl apply -k k8s/boss-api/environments/local-dev -n boss
		#kubectl apply -k k8s/boss-fs-api/environments/local-dev -n boss
		#kubectl apply -k k8s/boss-fs-dummy/environments/local-dev -n boss
		#kubectl apply -k k8s/boss-analytics/environments/local-dev -n boss
		#kubectl apply -k k8s/boss-analytics/environments/local-dev -n boss
		#kubectl apply -k k8s/boss-streaming-migration/environments/local-dev -n boss

deploy: publish apply

publishIt:
		#docker build . -f boss-integration-test/Dockerfile -t boss-integration-test:${BOSS_VERSION}
		#kind load docker-image boss-integration-test:${BOSS_VERSION} --name=${CLUSTER_NAME}

applyIt:
		#kubectl apply -k k8s/boss-integration-test/environments/local-dev -n boss

deployIt: publishIt applyIt
