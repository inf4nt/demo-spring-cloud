HOME=$PWD

cd docker
docker compose build

cd $HOME/integration-test
mvn test
