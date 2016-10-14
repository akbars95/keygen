keygenApp.controller("registerCtrl", function ($scope, $http, contextPath, restPath) {

    $scope.passwordStatusIsValid = $scope.userPassword != $scope.confirmUserPassword;
    $scope.checkPassword = function () {
        $scope.passwordStatusIsValid = $scope.userPassword == $scope.confirmUserPassword;
    };

    $scope.defaultData = function (booleanObj) {
        var number = "";
        if (booleanObj) {
            number = Math.round(Math.random() * 100);
        }

        $scope.userEmail = "userEmail" + number + "@mail.md";
        $scope.userName = "userName" + number;
        $scope.userPassword = "user#1M" + number;
        $scope.confirmUserPassword = "user#1M" + number;
    };

    $scope.send = function () {

        var dataObject = {
            userEmail: $scope.userEmail,
            createdDate: getDate(new Date()),
            userName: $scope.userName,
            userPassword: $scope.userPassword,
            confirmUserPassword: $scope.confirmUserPassword
        };

        //http://localhost:8999/keygeneration/api/desktop/register
        $http.post(contextPath + restPath + "/desktop/register", dataObject)
            .then(
                function (response) {
                    if (response.status = 200 && response.data && response.data.object == true) {
                        $scope.userEmail = "";
                        $scope.userName = "";
                        $scope.userPassword = "";
                        $scope.confirmUserPassword = "";
                        $scope.registrateResult = {
                            hasError: false,
                            description: response.data.messageErrorDescription
                        };
                    } else {
                        $scope.registrateResult = {
                            hasError: true,
                            description: response.data.messageErrorDescription
                        };
                    }
                },
                function (response) {
                    $scope.registrateResult = {
                        hasError: false,
                        description: response.data.messageErrorDescription
                    };
                }
            );
    }

});

keygenApp.controller("requestToKeygenCtrl", function ($scope, $http, contextPath, restPath) {

    $scope.successValidation = false;
    $scope.chooseUsernameOrUserEmail = true;

    /*$scope.chooseUsernameOrUserEmailF = function (bool) {
        if(bool){
            $scope.userName = null;
            $scope.validation();
        }else{
            $scope.userEmail = null;
            $scope.validation();
        }
    };*/

    $scope.validation = function () {
        if (($scope.userEmail && $scope.userName && $scope.userPassword) ||
            (($scope.userEmail || $scope.userName) && $scope.userPassword)) {
            $scope.successValidation = true;
        } else {
            $scope.successValidation = false;
        }
    };

    $scope.requestToKeygen = function () {

        var dataObject = {
            userEmail: $scope.userEmail,
            createdDate: getDate(new Date()),
            userName: $scope.userName,
            userPassword: $scope.userPassword
        };

        //http://localhost:8999/keygeneration/api/desktop/request
        $http.post(contextPath + restPath + "/desktop/request", dataObject)
            .then(
                function (response) {
                    if (response.status = 200 && response.data && response.data.object == true) {
                        $scope.userEmail = "";
                        $scope.userName = "";
                        $scope.userPassword = "";
                        $scope.registrateResult = {
                            hasError: false,
                            description: response.data.messageErrorDescription
                        };
                    } else {
                        $scope.registrateResult = {
                            hasError: true,
                            description: response.data.messageErrorDescription
                        };
                    }
                },
                function (response) {
                    $scope.registrateResult = {
                        hasError: false,
                        description: response.data.messageErrorDescription
                    };
                }
            );
    }

});

keygenApp.controller("getKeyCtrl", function ($scope, $http, contextPath, restPath) {
    $scope.successValidation = false;
    $scope.responseKey = undefined;

    $scope.validation = function () {
        if ($scope.userEmail && $scope.userName && $scope.tempPassword) {
            $scope.successValidation = true;
        } else if (($scope.userEmail || $scope.userName) && $scope.tempPassword) {
            $scope.successValidation = true;
        } else {
            $scope.successValidation = false;
        }
    };

    $scope.sendGetKey = function () {

        var ar = window.location.pathname.split("/");


        var dataObject = {
            userEmail: $scope.userEmail,
            userName: $scope.userName,
            createdDate: getDate(new Date()),
            tempPathURL: ar[ar.length - 1],
            userPassword: $scope.tempPassword
        };

        //http://localhost:8999/keygeneration/api/desktop/request/
        $http.post(contextPath + restPath + "/desktop/getkey", dataObject)
            .then(
                function (response) {
                    if (response.status = 200 && response.data && response.data.object) {
                        $scope.userEmail = "";
                        $scope.userName = "";
                        $scope.tempPassword = "";
                        $scope.responseKey = response.data.object;
                        $scope.registrateResult = {
                            hasError: false,
                            description: response.data.messageErrorDescription
                        };
                    } else {
                        $scope.registrateResult = {
                            hasError: true,
                            description: response.data.messageErrorDescription
                        };
                    }
                },
                function (response) {
                    $scope.registrateResult = {
                        hasError: false,
                        description: response.data.messageErrorDescription
                    };
                }
            );
    }

});
