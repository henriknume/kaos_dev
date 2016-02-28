'use strict';

//Here we define a module for our controllers. The array contains other 
//modules this one depends on. 
var chatControllers = angular.module('chatControllers', []);

//This is our main controller. Our only controller at the moment. 
chatControllers.controller('mainCtrl', ['$scope', function($scope){
        
        /*Here we can declare any variables or function we want to 
          access from the HTML page. */
        
        //List of all teams
        $scope.teams = ['kaosdev', 'slackerz', 'chalmerz']; 
        
        //List of all chat messages and the time of their posting
        $scope.log = [{
            text: 'Welcome to this awesome chat site!', 
            date: new Date(),
            avatar: '../app/img/system.png'
            }];
        
        //This function is called when the user selects a team chat
        $scope.enterChatRoom = function(team){
            $scope.log.push({
                text: 'Welcome to the chat chanel for ' + team,
                date: new Date(),
                avatar: '../app/img/system.png'             
            });
        };
        
        //This function is called when the user writes a new message in a chat
        $scope.writeMessage = function(text){
            $scope.log.push({
                text: text,
                date: new Date(),
                avatar: '../app/img/profile-icon.png'
            });
        };
}]);