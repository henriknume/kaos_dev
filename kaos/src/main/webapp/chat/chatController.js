'use strict';

//Here we define a module for our controllers. The array contains other 
//modules this one depends on. 
var chat = angular.module('chat', []);

chat.controller('ChatController', 
    ['$scope', '$rootScope', 'UserService', 'TeamService',
    function($scope, $rootScope, UserService, TeamService){
        
        /*Here we can declare any variables or function we want to 
          access from the HTML page. */
        
        $scope.chatStatus = 'none';
        
        $scope.currentUser = $rootScope.globals.currentUser.username;
        
        $scope.currentTeam = null;
        
        $scope.currentTeamMembers = null;
        
        $scope.teams = ['kaosdev', 'slackerz', 'chalmerz']; //UserService.getTeamsByLogin();
        
        //constant string
        $scope.pm_title = "";
                        
        //List of all chat messages
        $scope.log = [{ 
            text: 'Welcome ' + $scope.currentUser + '!', 
            date: new Date(),
            sender: "System", 
            avatar: 'img/system.png'
            }];
        
        //This function is called when the user selects a team chat
        $scope.enterChatRoom = function(team){
            $scope.chatStatus = "team";
            $scope.currentTeam = team;
            //$scope.currentTeamMembers = TeamService.getTeamMembers(currentTeam);
            $scope.pm_title = "Click on one of your following team members to start chatting!";
            $scope.log = [{         //TeamService.getMessageLogByTeam($scope.currentTeam);
                text: 'Welcome to the chat chanel for ' + team,
                date: new Date(),
                sender: 'System',
                avatar: 'img/system.png'             
            }];
        };
        
        //This function is called when the user selects a private chat
        $scope.enterPrivateChatRoom = function(member){
            $scope.chatStatus = "private";
            $scope.log = [{         
                text: 'Welcome to the chat chanel for ' + member,
                date: new Date(),
                sender: "system",
                avatar: 'img/system.png'         
            }];
        };
        
        //This function is called when the user submits text throught the chat box
        $scope.writeMessage = function(text){  
            if($scope.chatStatus === "team"){
                $scope.log.push({               //TeamService.sendMessageToTeam($scope.currentTeam, text);
                    text: text,
                    date: new Date(),
                    sender: $scope.currentUser,
                    avatar: 'img/profile-icon.png'
                });
            }else if($scope.chatStatus === "private"){
                 $scope.log.push({               //TODO: XService.sendMessageToUser(user, text);
                    text: text,
                    date: new Date(),
                    sender: $scope.currentUser,
                    avatar: 'img/profile-icon.png'
                });
            }else if($scope.chatStatus === "none"){
                $scope.log.push({   
                    text: "You are not in a team chat!",
                    date: new Date(),
                    sender: "System",
                    avatar: 'img/system.png'
                });
            }
            document.getElementById("myForm").reset();
        };        
}]);

/*chat.directive('scrollBar', function () {
  return {
    restrict: 'A',
    replace: false,
    scope: {
      scrollBar: "="
    },
    link: function (scope, element) {
      scope.$watchCollection('scrollBar', function (newValue) {
        if (newValue)
        {
          $(element).scrollTop($(element)[0].scrollHeight);
        }
      });
    }
  };
});*/