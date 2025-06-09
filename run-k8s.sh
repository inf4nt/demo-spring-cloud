minikube delete

minikube start

docker build -t api-spring-cloud api-spring-cloud/ -f docker/java21.Dockerfile

docker build -t client-spring-cloud client-spring-cloud/ -f docker/java21.Dockerfile

minikube image load api-spring-cloud:latest

minikube image load client-spring-cloud:latest

kubectl apply -f k8s/api-spring-cloud.deployment.service.yaml

kubectl apply -f k8s/client-spring-cloud.configmap.yaml

kubectl apply -f k8s/client-spring-cloud.deployment.service.yaml

echo -e '\033[0;31mrun "minikube service --all" to open a tunnel'
