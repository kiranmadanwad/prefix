# Problem Statement for IP Prefix Lookup Optimal Solution

Assume there's a script that collects a list of IP subnets owned by all the public service providers. These IP subnets with their prefixes are provided in `prefixes.json` file. Each subnet has a Cloud Service Provider and some tag(s) attached to them. Each IP Subnet will consist of multiple IP addresses, based on the prefix. The biggest subnet we can have is /8 (16,777,216 IPs) and smallest subnet is /32 (1 IP).

As a user, we are going to provide either a single IP address or list of IP addresses via REST API. These IP(s) might or might not belong to the above IP subnets. Also these (IPs) could belong to multiple subnets. The expected output is name of the Cloud Service Provider and related tag(s), if matched for user input.

Example: The provided data contains “184.51.33.0/24” with “Akamai” as provider and “Cloud”, “CDN/WAF” as tags. So if user provides “184.51.33.230” as an input, the API should return something like `{“result”: [{“subnet”: “184.51.33.0/24”, “provider”: “Akamai”, “tags”: [“Cloud”, “CDN/WAF”]}]}` as output. As mentioned above, there could be multiple matching subnets.

Your task is to find a solution that's:
* The most efficient way to store this data. 
* The fast way to figure out if a user provided IP address belongs to a certain Cloud Provider Prefix or not?
* The ideal time for looking this up should be less than ~300ms for a batch of 10 IP addreses.
* Searching a single IP address should be less than ~50ms. (excluding N/W trip time)
* A single IP could belong to multiple subnets too.
* You can prepare/model the data as per your preference, if needed.
* Feel free to use any database for the task. Using a database is not mandatory.


Build RESTful endpoint(s) to search in Python (Flask RESTful/FastAPI preferred):
 * Single IP address
 * Multiple IP addresses (batch)

Follow the RESTful design standards and coding standards. The code should be up to prod standards and follow best practices.

## References

You can read more about IP Prefixes and Subnets here:
* https://www.cloudflare.com/learning/network-layer/what-is-a-subnet/
* https://medium.com/netdevops/what-are-network-prefixes-e1923a1d6a3e
* https://avinetworks.com/glossary/subnet-mask/
* https://www.calculator.net/ip-subnet-calculator.html


# Spring Boot Prefix Lookup Application

This Spring Boot application provides two APIs for prefix lookup operations. It allows you to perform single prefix lookup using a GET request and batch prefix search using a POST request. Additionally, you can containerize and run this application using Docker for easy deployment.

## Table of Contents

- [API Endpoints](#api-endpoints)
  - [GET /v1/prefixes/lookup/{id}](#get-v1prefixeslookupid)
  - [POST /v1/prefixes/lookup - Batch Search](#post-v1prefixeslookup---batch-search)
- [Building and Running with Docker](#building-and-running-with-docker)
- [Accessing the Swagger UI](#accessing-the-swagger-ui)

## API Endpoints

### GET /v1/prefixes/{id}

Use this endpoint to perform a single prefix lookup by providing the `{id}` parameter.

#### Example Request

```http
GET /v1/prefixes/23.79.237.4
```

#### Example Response

```json
{
    "result": [
        {
            "subnet": "23.79.237.0/24",
            "provider": "Akamai",
            "ip": "23.79.237.4",
            "tags": [
                "Cloud",
                "CDN/WAF"
            ]
        }
    ]
}
```

### POST /v1/prefixes - Batch Search

This endpoint allows you to perform batch prefix searches by sending a POST request with a list of prefix IDs in the request body.

#### Example Request

```http
POST /v1/prefixes
Content-Type: application/json

  {
   "ips" :["104.198.128.1",
    "34.157.208.1"
   ]
}

```

#### Example Response

```json
{
    "result": [
        {
            "subnet": "2.22.60.0/24",
            "provider": "Akamai",
            "ip": "2.22.60.2",
            "tags": [
                "Cloud",
                "CDN/WAF"
            ]
        },
        {
            "subnet": "3.11.53.0/24",
            "provider": "AWS",
            "ip": "3.11.53.1",
            "tags": [
                "Cloud",
                "CDN"
            ]
        },
  ]
}
```

## Building and Running with Docker

To build and run this application using Docker, follow these steps:

1. Clone this repository to your local machine.

2. Open a terminal and navigate to the root of the repository.

3. Build the Docker image with the following command:

```bash
docker build -t prefixlookup .
```

4. After the Docker image is built, you can run the application with Docker Compose using the following command:

```bash
docker-compose up
```

The Spring Boot application will start and be accessible at `http://localhost:8080`.

## Accessing the Swagger UI

Once the service is up and running, you can access the Swagger UI to interact with the APIs and explore their documentation. Use the following link in your web browser:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)

This UI provides a user-friendly interface to test and understand the functionality of the API endpoints.

## Verfied response time to meet the requirement 
1. GET Api
  ![image](https://github.com/kiranmadanwad/prefix/assets/29003308/ba3a9e38-f270-43ad-85fc-154c45d35456)

2. POST Api
  ![image](https://github.com/kiranmadanwad/prefix/assets/29003308/d0ae6cbe-da40-4c94-bdc0-db30889936e6)


Feel free to use and customize this README for your Spring Boot Prefix Lookup Application. Happy coding!
## 
