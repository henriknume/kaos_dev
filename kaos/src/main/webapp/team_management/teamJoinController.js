'use strict';

var chat = angular.module('chat');

chat.controller('TeamJoinController', 
    ['$scope', '$rootScope', '$location', 'UserService', 'TeamService', 'FlashService',
    function($scope, $rootScope, $location, UserService, TeamService, FlashService){
        
        $scope.title = "Search for a team to join";
        
        $scope.submitText = "Join";
        
         $scope.run = function(){
            var response;
            
            TeamService.getTeamByName({team_name: $scope.team_name, password: $scope.password, login: $rootScope.globals.currentUser.username})
            .success(function(){
                    FlashService.Success('Join successful', true);
                    $location.path('/');
                }).error(function(response){
                    FlashService.Error("An error occurred: " + response.message);
                    $scope.dataLoading = false;
                });
        };
    }]
);