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
            AuthenticationService.Login($scope.username, $scope.password, function (response) {
                if (response.success) {
                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                    $location.path('/');
                } else {
                    FlashService.Error(response.message);
                    $scope.dataLoading = false;
                }
            });
        };
    }
]);
