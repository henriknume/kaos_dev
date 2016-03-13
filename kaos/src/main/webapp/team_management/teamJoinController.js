'use strict';

angular.module('chat').controller('TeamJoinController', 
    ['$scope', '$rootScope', '$location', 'TeamService', 'FlashService',
    function($scope, $rootScope, $location, TeamService, FlashService){
        
        //static strings
        $scope.title = "Search for a team to join";        
        $scope.submitText = "Join";
        
         $scope.run = function(){
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