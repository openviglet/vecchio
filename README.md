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
$ mvn jetty:run
```

# Test

## Mapping Resources

For example, in Mapping Console ([http://localhost:8080/console/#!/mapping](http://localhost:8080/console/#!/mapping)) add the following URLs:

1. Viglet Turing Entity
	- URL: [/turing/entity](http://localhost:8080/turing/entity)
	- Proxy: [https://api.viglet.ai/turing/entity](https://api.viglet.ai/turing/entity)
2. GitHub - openviglet
	- URL: [/github/openviglet](http://localhost:8080/github/openviglet)
	- Proxy: [https://api.github.com/users/openviglet](https://api.github.com/users/openviglet)

![mapping.png](https://openviglet.github.io/vecchio/img/mapping.png)

## Grant Types
Create a new App ([http://localhost:8080/console/#!/app/new](http://localhost:8080/console/#!/app/new)).

Will generate the following tokens for your App, for instance:

* **Consumer Key (API Key)**: `8d0304f37e2c0bbf1d9ab602e916ef34`
* **Consumer Secret (API Secret)**:	`c640f16520015c5a599f666107e4ce25`
* **Access Token**: `fb803d96bb541ba658d514b3f4d88363`
* **Access Token Secret**: `43c713bcbdb7a54962a1b4abcfb8af58`

### Authorization Code

Use the API Key to generate the Authorization Code:

```shell
curl -I -X GET 'http://localhost:8080/api/authorize?response_type=code&client_id=8d0304f37e2c0bbf1d9ab602e916ef34&redirect_uri=http://localhost:8080/console/oauth2/receive_authcode'
```
Will return the location, so get the authorization code:

```shell
Location: http://localhost:8080/console/oauth2/receive_authcode?code=e31d6626d203aaea0811305e33136d59
```

Use the authorization code to generate the access token:

```shell
curl -I -X GET 'http://localhost:8080/api/token' -d 'grant_type=authorization_code&code=e31d6626d203aaea0811305e33136d59'
```

### Implicit

Use the API Key to generate the Authorization Code:

```bash
curl -I -X GET 'http://localhost:8080/api/authorize?response_type=token&client_id= 8d0304f37e2c0bbf1d9ab602e916ef34&redirect_uri=http://localhost:8080/console/oauth2/receive_implicit_token'
```

Will return the location, so get the access token:

```shell
Location: http://localhost:8080/console/oauth2/receive_implicit_token?access_token=2YotnFZFEjr1zCsicMWpAA&state=xyz&token_type=bearer&expires_in=3600
```

## Using Resources
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

Dashboard Console ([http://localhost:8080/console/#!/dashboard](http://localhost:8080/console/#!/dashboard)) shows response time report.

![dashboard.png](https://openviglet.github.io/vecchio/img/dashboard.png)