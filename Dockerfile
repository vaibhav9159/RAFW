# use a base image with Maven and Java
FROM maven:3.8.3-openjdk-11

# set the  working directory inside container
WORKDIR /app

# copy the maven project files inside container
COPY pom.xml .
COPY src ./src

# switch to directory containing pom.xml
WORKDIR /app

# run maven tests
CMD ["mvn","test"]

