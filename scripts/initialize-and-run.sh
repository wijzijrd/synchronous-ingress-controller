docker build . -t service-a
docker run -p 8080:8080 service-a

docker build . -t service-b
docker run -p 8080:8080 service-b

docker build . -t service-c
docker run -p 8080:8080 service-c

docker build . -t service-d
docker run -p 8080:8080 service-d