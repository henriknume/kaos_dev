'use strict';

angular.module('app').controller('LoginController', 
    ['$scope', '$location', 'AuthenticationService', 'FlashService',
    function ($scope, $location, AuthenticationService, FlashService) {

        $scope.login = login;

        (function initController() {
            // reset login status
            AuthenticationService.ClearCredentials();
        })();

        function login() {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.user, function (response) {
                if (response.success) {
                    AuthenticationService.SetCredentials($scope.user.login, $scope.user.password);
                    $location.path('/');
                } else {
                    FlashService.Error("An error occurred: " + response.message);
                    $scope.dataLoading = false;
                }
            });
        };
    }
]);
