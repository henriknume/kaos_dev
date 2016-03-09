'use strict';

angular.module('app').controller('RegisterController', 
    ['$scope', 'UserService', '$location', '$rootScope', 'FlashService',
        function ($scope, UserService, $location, $rootScope, FlashService){

            $scope.register = function () {
                $scope.dataLoading = true;
                UserService.createUser($scope.user)
                    .success(function(response){
                        FlashService.Success('Registration successful', true);
                        $location.path('/login');
                    }).error(function(response){
                        FlashService.Error("An errror occurrd: " + response.message);
                        $scope.dataLoading = false;
                    });
            };            
        }
    ]
);
