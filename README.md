![vecchio_banner.png](https://openvecchio.github.io/vecchio/img/vecchio_banner.png)
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
$ docker run -d -p 2702:2702 --name vecchio viglet/vecchio
```

# Option 2. Installing manually 

## Download

```shell
$ git clone --recurse-submodules https://github.com/openvecchio/vecchio.git
$ cd vecchio
```

## Deploy 

### 1. Runtime

Use Gradle to execute Vecchio API, without generate jar file.

```shell
$ ./gradlew bootrun
```


### 2. Or Generate JAR File

Use Gradle to generate Vecchio API executable JAR file.

```shell
$ ./gradlew build
```

#### 2.1 Run

To run Vecchio API executable JAR file, just execute the following line:

```shell
$ java -jar build/libs/viglet-vecchio.jar
```

# Test

## Mapping Resources

For example, in Mapping Console ([http://localhost:2702/console/#!/mapping](http://localhost:2702/console/#!/mapping)) add the following URL:

GitHub - openviglet
	- URL: [/github/openviglet](http://localhost:2702/github/openviglet)
	- Proxy: [https://api.github.com/users/openviglet](https://api.github.com/users/openviglet)

![mapping.png](https://openvecchio.github.io/vecchio/img/mapping.png)

## Apps

Create a new App ([http://localhost:2702/console/#!/app/new](http://localhost:2702/console/#!/app/new)) and copy the values of  "Keys and Access Tokens" tab.

![vecchio_oauth2.png](https://openvecchio.github.io/vecchio/img/vecchio_oauth2.png)

Will generate the following keys an tokens for your App, for instance:

| Token                        | Value                            | 
| ---------------------------- |:--------------------------------:|
| Consumer Key (API Key)       | 8d0304f37e2c0bbf1d9ab602e916ef34 |
| Consumer Secret (API Secret) | c640f16520015c5a599f666107e4ce25 |
| Access Token 				   | fb803d96bb541ba658d514b3f4d88363 |
| Access Token Secret          | 43c713bcbdb7a54962a1b4abcfb8af58 |

## Grant Types

You can access the API Resources using your Access Token, ou generate new Access Token using the followings Grant Types:

### Authorization Code

Use the **Consumer Key (API Key)** to generate the *Authorization Code*:

```bash
curl -I -X GET 'http://localhost:2702/oauth/authorize?response_type=code&client_id=8d0304f37e2c0bbf1d9ab602e916ef34&redirect_uri=http://localhost:2702/console/oauth2/receive_authcode'
```
Will return the *Location*, so get the **Authorization Code**:

```bash
Location: http://localhost:2702/console/oauth2/receive_authcode?code=e31d6626d203aaea0811305e33136d59`
```

Use the *Authorization Code* to generate the **Access Token**, for instance: `b516216e45610d4be3716c8dfab70985`:

```bash
curl -X GET 'http://localhost:2702/oauth/token' -d 'grant_type=authorization_code&code=e31d6626d203aaea0811305e33136d59'
```

### Implicit

Use the **Consumer Key (API Key)** to generate the *Authorization Code*:

```bash
curl -I -X GET 'http://localhost:2702/oauth/authorize?response_type=token&client_id=8d0304f37e2c0bbf1d9ab602e916ef34&redirect_uri=http://localhost:2702/console/oauth2/receive_implicit_token'
```

Will return the *Location*, so get the **Access Token**, for instance: `b516216e45610d4be3716c8dfab70985`:

```bash
Location: http://localhost:2702/console/oauth2/receive_implicit_token?access_token=b516216e45610d4be3716c8dfab70985&state=xyz&token_type=bearer&expires_in=3600
```

## Using API Resources

Ready! You can access the following API Resources using the **Access Token** from Authorization Code or Implicit Grant Types or App Settings (Your Access Token), for instance `fb803d96bb541ba658d514b3f4d88363`, if you use an invalid Access Token, it will return empty response.

* GitHub - openviglet:

```bash
curl -X GET "localhost:2702/github/openviglet" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: Bearer fb803d96bb541ba658d514b3f4d88363"
```

# Dashboard

Dashboard Console ([http://localhost:2702/console/#!/dashboard](http://localhost:2702/console/#!/dashboard)) shows response time report.

![dashboard.png](https://openvecchio.github.io/vecchio/img/dashboard.png)