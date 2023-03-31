# product-service

## Project structure overview

```bash
├── README.md
├── TestExampleFile.csv   # test data file, will be used by DataInitializer.java
├── database  # please cd to this folder and run [docker-compose up -d] to pull up the database and pgadmin
│   └── docker-compose.yml  # I make minor change to the network part since I do not have solysystem network at my local, change to use bridge network. please use host.docker.internal as host when connect within pgadmin
├── pom.xml   # init from https://start.spring.io/
└── src
    └── main
        ├── java
        │   └── com
        │       └── demo
        │           └── productservice
        │               ├── DataInitializer.java    # SpringBootApplication implements CommandLineRunner, load the data file and insert data to test DB
        │               ├── ProductServiceApplication.java    # SpringBootApplication for the rest service, provide the APIs
        │               ├── controller # package for Rest Controller
        │               │   ├── CategoryResource.java   # RestController for Category
        │               │   └── ProductResource.java    # RestController for Product
        │               ├── entity  # package for Entity
        │               │   ├── Category.java
        │               │   └── Product.java
        │               ├── exception # package for Exception
        │               │   └── ProductNotFoundException.java # Customized Exception when not able to find the Product with product code
        │               └── jpa # package for JPA repository
        │                   ├── CategoryRepository.java
        │                   └── ProductRepository.java
        └── resources
            └── application.properties
```

from running with: `tree -I 'pgadmin|postgres_data|target|mvnw|mvnw.cmd|HELP.md|test|*.iml' --prune`

## Steps to run

1. go to the database folder and run `docker-compose up -d`
2. checking the pgAdmin started at `http://localhost:8081/browser/`
3. execute the `DataInitializer.mian`
4. checking in pgAdmin data inserted into the product and category table under database test
5. run the `ProductServiceApplication.mian` to start the product service
6. checking the API doc: `http://localhost:8080/swagger-ui/index.html`
