![banner_vecchio.jpg](https://openviglet.github.io/vecchio/img/banner_vecchio.jpg)
------
**Viglet Vecchio** allows to create authentication and authorization layers to access API.

**If you'd like to contribute to Viglet Vecchio, be sure to review the [contribution
guidelines](CONTRIBUTING.md).**

**We use [GitHub issues](https://github.com/openviglet/vecchio/issues) for
tracking requests and bugs.**

# Option 1. Docker
1. Install the Docker. [https://docs.docker.com/engine/installation](https://docs.docker.com/engine/installation)


2. Pull the Viglet Vecchio Docker and run it.

```shell
$ docker pull viglet/vecchio
$ docker run -d -p 8080:8080 --name vecchio viglet/vecchio
```

# Option 2. Installing manually 

## Maven
* Install the Apache Maven. [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

## Execute

* To run Viglet Vecchio, execute the following command:

`$ mvn jetty:run
`

# Test

Ready! You can access the following APIs using the token `access_token_valid` , if you use different token, returns empty response.

* Viglet Turing - Entity:

`curl -X GET "http://localhost:8080/turing/entity" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: Bearer access_token_valid"
`

* GitHub - openviglet:

` curl -X GET "localhost:8080/github/openviglet" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: Bearer access_token_valid"
`