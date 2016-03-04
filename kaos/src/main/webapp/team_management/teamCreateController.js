'use strict';

var chat = angular.module('chat');

chat.controller('TeamCreateController', 
    ['$scope', '$rootScope', 'UserService', 'TeamService', 'FlashService',
    function($scope, $rootScope, UserService, TeamService, FlashService){
        
        $scope.title = "Create a new team";
        
        $scope.submitText = "Create";
        
        $scope.run = function(){
            $scope.dataLoading = true;
            TeamService.createTeam({team_name: $scope.team_name, password: $scope.password})
                .then(function (response) {
                    if (response.success) {
                        FlashService.Success('Creation successful', true);
                        //TODO: Return to main page and enter team chat
                    } else {
                        FlashService.Error(response.message);
                        $scope.dataLoading = false;
                    }
                });
        };
        
    }]
);