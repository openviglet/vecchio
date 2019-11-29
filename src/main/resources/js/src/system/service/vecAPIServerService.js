vecchioApp.service('vecAPIServerService', [
    '$http'
    , '$location'
    , '$cookies'
    , function ($http, $location, $cookies) {
        var vecProtocol = $location.protocol();
        var vecHostname = $location.host();
        var vecPort = $location.port();
        var vecAPIContext = "/api";
        var vecEmbServer = vecProtocol + "://" + vecHostname + ":" + vecPort;
        var vecEmbAPIServer = vecEmbServer + vecAPIContext;
        this.server = function () {
            if ($cookies.get('vecServer') != null) return $cookies.get('vecServer');
            else {
                $cookies.put('vecServer', vecEmbServer);
                return vecEmbServer;
            }
        }
        this.get = function () {
            if ($cookies.get('vecAPIServer') != null) return $cookies.get('vecAPIServer');
            else {
                $cookies.put('vecAPIServer', vecEmbAPIServer);
                return vecEmbAPIServer;
            }
        }
    }]);
