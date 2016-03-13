'use strict';

angular.module('app').controller('LoginController', 
    ['$scope', '$location', 'AuthenticationService', 'UserService', 'FlashService',
    function ($scope, $location, AuthenticationService, UserService, FlashService) {

        $scope.login = login;

        (function initController() {
            // reset login status
            AuthenticationService.ClearCredentials();
        })();

        function login() {
            $scope.dataLoading = true;            
            UserService.getUserByLogin($scope.user)
                .success(function(responseUser){                    
                    if(responseUser.password === $scope.user.password){
                        AuthenticationService.SetCredentials($scope.user.login, $scope.user.password);
                        $location.path('/');
                    }else{
                        FlashService.Error("Username or password is incorrect");
                        $scope.dataLoading = false;
                    }                    
                }).error(function(){
                    FlashService.Error("Username or password is incorrect");
                    $scope.dataLoading = false;
                });  
        };
    }
]);
