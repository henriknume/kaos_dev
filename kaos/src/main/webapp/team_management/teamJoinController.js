'use strict';

var chat = angular.module('chat');

chat.controller('TeamJoinController', 
    ['$scope', '$rootScope', 'UserService', 'TeamService', 'FlashService',
    function($scope, $rootScope, UserService, TeamService, FlashService){
        
        $scope.title = "Search for a team to join";
        
        $scope.submitText = "Join";
        
         $scope.run = function(){
            var response;
            
            TeamService.getTeamByName($scope.team_name)
            .then(function (team) {
                    if (team !== null && team.password === $scope.password) {
                        response = { success: true };
                    } else {
                        response = { success: false, message: 'Team does not exist or password is incorrect' };
                    }
                    
                    if (response.success) {
                     //TODO: Return to main page and enter team chat
                    } else {
                        FlashService.Error(response.message);
                        $scope.dataLoading = false;
                    }
            });
        };
    }]
);