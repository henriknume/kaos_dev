'use strict';

var chat = angular.module('chat');

chat.controller('TeamCreateController', 
    ['$scope', '$rootScope', '$location', 'UserService', 'TeamService', 'FlashService',
    function($scope, $rootScope, $location, UserService, TeamService, FlashService){
        
        $scope.title = "Create a new team";
        
        $scope.submitText = "Create";
        
        $scope.run = function(){
            $scope.dataLoading = true;
            TeamService.createTeam({team_name: $scope.team_name, password: $scope.password}, $rootScope.globals.currentUser.username)
                .success(function(){
                    FlashService.Success('Creation successful', true);
                    $location.path('/');
                }).error(function(response){
                    FlashService.Error("An error occurred: " + response.message);
                    $scope.dataLoading = false;
                });
        };
        
    }]
);