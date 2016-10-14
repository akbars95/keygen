/**
 * Created by dminzat on 8/11/2016.
 */
keygenApp.config(function($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl : "/",
            controller: 'RouteController'
        })
        .when("/registration", {
            templateUrl : "/register",
            controller: 'registerCtrl'
        })
        .when("/requesting", {
            templateUrl : "/request_to_keygen",
            controller: 'requestToKeygenCtrl'
        })
        .otherwise({redirectTo: '/'});
});