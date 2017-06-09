![banner_vecchio.jpg](https://openviglet.github.io/vecchio/img/banner_vecchio.jpg)
------
**Viglet Vecchio** allows to create authentication and authorization layers to access API.

**If you'd like to contribute to Viglet Vecchio, be sure to review the [contribution
guidelines](CONTRIBUTING.md).**

**We use [GitHub issues](https://github.com/openviglet/vecchio/issues) for
tracking requests and bugs.**

# Option 1. Docker
1. Install Docker. [https://docs.docker.com/engine/installation](https://docs.docker.com/engine/installation)


2. Pull Viglet Vecchio Docker and run it.

```shell
$ docker pull viglet/vecchio
$ docker run -d -p 8080:8080 --name vecchio viglet/vecchio
```

# Option 2. Installing manually 

## Maven
Install Apache Maven. [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

## Bower
Bower is a command line utility. Install it with npm.

```shell
$ npm install -g bower
```

Bower requires node, npm and git.

More details: [https://bower.io/#install-bower](https://bower.io/#install-bower)

## Execute

Execute the following command to run Viglet Vecchio:

```shell
$ mvn jetty:run-forked
```

# Test
For example, in Mapping Console ([http://localhost:8080/console/#!/home/config/mapping](http://localhost:8080/console/#!/home/config/mapping)) add the following URLs:

1. Viglet Turing Entity
	- URL: [/turing/entity](http://localhost:8080/turing/entity)
	- Proxy: [https://api.viglet.ai/turing/entity](https://api.viglet.ai/turing/entity)
2. GitHub - openviglet
	- URL: [/github/openviglet](http://localhost:8080/github/openviglet)
	- Proxy: [https://api.github.com/users/openviglet](https://api.github.com/users/openviglet)

![mapping.png](https://openviglet.github.io/vecchio/img/mapping.png)

Ready! You can access the following APIs using the token `access_token_valid` , if you use different token, returns empty response.

* Viglet Turing - Entity:

```shell
curl -X GET "http://localhost:8080/turing/entity" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: Bearer access_token_valid"
```

* GitHub - openviglet:

```shell
curl -X GET "localhost:8080/github/openviglet" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: Bearer access_token_valid"
```

# Dashboard

In Dashboard Console ([http://localhost:8080/console/#!/home/dashboard](http://localhost:8080/console/#!/home/dashboard)) shows response time report.

![dashboard.png](https://openviglet.github.io/vecchio/img/dashboard.png)