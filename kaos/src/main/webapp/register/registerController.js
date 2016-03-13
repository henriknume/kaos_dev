'use strict';

angular.module('app').controller('RegisterController', 
    ['$scope', 'UserService', '$location', 'FlashService',
        function ($scope, UserService, $location, FlashService){

            $scope.register = function () {
                $scope.dataLoading = true;
                UserService.createUser($scope.user)
                    .success(function(){
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
