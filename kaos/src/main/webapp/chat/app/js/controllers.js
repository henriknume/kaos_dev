'use strict';

//Here we define a module for our controllers. The array contains other 
//modules this one depends on. 
var chatControllers = angular.module('chatControllers', []);


//This is our main controller. Our only controller at the moment. 
chatControllers.controller('mainCtrl', ['$scope', '$routeParams', 
    function($scope, $routeParams){
        
        /*Here we can declare any variables or function we want to 
          access from the HTML page. */
        
        $scope.currentUser = $routeParams.user;
        
        //List of all teams
        $scope.teams = ['kaosdev', 'slackerz', 'chalmerz']; 
        
        $scope.currentTeam;
        
        //List of all chat messages and the time of their posting
        $scope.log = [{
            text: 'Welcome ' + $scope.currentUser + '!', 
            date: new Date(),
            avatar: 'chat/app/img/system.png'
            }];
        
        //This function is called when the user selects a team chat
        $scope.enterChatRoom = function(team){
            $scope.currentTeam = team;
            $scope.log = [{
                text: 'Welcome to the chat chanel for ' + team,
                date: new Date(),
                avatar: 'chat/app/img/system.png'             
            }];
        };
        
        //This function is called when the user writes a new message in a chat
        $scope.writeMessage = function(text){
            $scope.log.push({
                text: text,
                date: new Date(),
                avatar: 'chat/app/img/profile-icon.png'
            });
        };
}]);


chatControllers.controller('loginCtrl', ['$scope', function($scope){
        
}]);