'use strict';

//Here we define a module for our controllers. The array contains other 
//modules this one depends on. 
var chat = angular.module('chat', []);

//This is our main controller. Our only controller at the moment. 
chat.controller('ChatController', ['$scope', '$rootScope', 'UserService', 
    function($scope, $rootScope, UserService){
        
        /*Here we can declare any variables or function we want to 
          access from the HTML page. */
        
        //List of all teams. 
        $scope.teams = ['kaosdev', 'slackerz', 'chalmerz']; 
        
        //Currently logged in user
        $scope.currentUser = $rootScope.globals.currentUser.username;
        
        //List of all chat messages and the time of their posting
        $scope.log = [{
            text: 'Welcome ' + $scope.currentUser + '!', 
            date: new Date(),
            avatar: 'img/system.png'
            }];
        
        //This function is called when the user selects a team chat
        $scope.enterChatRoom = function(team){
            $scope.log = [{
                text: 'Welcome to the chat chanel for ' + team,
                date: new Date(),
                avatar: 'img/system.png'             
            }];
        };
        
        //This function is called when the user writes a new message in a chat
        $scope.writeMessage = function(text){
            $scope.log.push({
                text: text,
                date: new Date(),
                avatar: 'img/profile-icon.png'
            });
        };        
}]);