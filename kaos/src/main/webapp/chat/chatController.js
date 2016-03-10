'use strict';

angular.module('chat', []).controller('ChatController', 
    ['$scope', '$rootScope', 'UserService', 'TeamService', 'MessageService',
    function($scope, $rootScope, UserService, TeamService, MessageService){                
        
        $scope.chatStatus = 'none';//Used for knowing whether user is in chat chanel or not 
        
        $scope.currentUser = $rootScope.globals.currentUser;
        
        $scope.currentTeam = null;//The team chanel currentUser is in right now
        
        $scope.currentTeamMembers = null;//List of members of the team chanel currentUser is in right now  
        
        $scope.teams = [];  //List of all teams currentUser has joined                 
        
        $scope.pm_title = "";//Title string displayed in sidebar                               
        
        $scope.currentPrivateChat = null;//The name of the user that currentUser is chatting with       
        
        $scope.log = [{ //List of all chat messages
            text: 'Welcome ' + $scope.currentUser.username + '!', 
            date: new Date(),
            sender: "System", 
            avatar: 'img/system.png'
        }];
    
        //Code to initialize list of teams
        UserService.getTeamsByLogin($scope.currentUser.username)
            .success(function(response){
                var listOfTeams = response;
                var listOfTeamNames = [];

                for(var i = 0; i < listOfTeams.length; i++){
                    listOfTeamNames.push(listOfTeams[i].name);
                }
               $scope.teams = listOfTeamNames;
            }).error(function(response){ 
                console.log(response); 
                confirm("An error occurred: " + response.message);
                $scope.teams = [];
            });        
            /*
        //Start polling
        setInterval(function(){
            if($scope.chatStatus === "team"){
                $scope.log = $scope.getTeamMessageLog($scope.currentTeam);
            }else if($scope.chatStatus === "private"){
                $scope.log = $scope.getUserMessageLog($scope.currentPrivateChat);                
            }
        }, 1000*5);
        */
        //This function is called when the user selects a team chat
        $scope.enterChatRoom = function(team){
            $scope.chatStatus = "team";
            $scope.currentTeam = team;
            $scope.getTeamMembers(team);
            $scope.pm_title = "Click on one of your following team members to start chatting!";
            $scope.getTeamMessageLog(team);
                        
            //Default message
            if($scope.log === []){
                $scope.log = [{
                text: 'Welcome to the chat chanel for ' + team,
                date: new Date(),
                sender: 'System',
                avatar: 'img/system.png'             
                }];
            }
        };
        
        //This function is called when the user selects a private chat
        $scope.enterPrivateChatRoom = function(member){
            $scope.chatStatus = "private";
            $scope.currentPrivateChat = member.login;
            $scope.getUserMessageLog(member.login);
            
            //Default message
            if($scope.log === []){
                $scope.log = [{    
                    text: 'Welcome to the chat chanel for ' + member,
                    date: new Date(),
                    sender: "system",
                    avatar: 'img/system.png'         
                }];
            }
        };
        
        //This function is called when the user submits text throught the chat box
        $scope.writeMessage = function(text){  
            if($scope.chatStatus === "team"){
                MessageService.sendMessageToTeam($scope.currentTeam, $scope.currentUser.username, text)
                    .then(function(){$scope.getTeamMessageLog($scope.currentTeam);});
            }else if($scope.chatStatus === "private"){
                MessageService.sendMessageToUser($scope.currentPrivateChat, $scope.currentUser.username, text)
                    .then(function(){$scope.getUserMessageLog($scope.currentPrivateChat);});  
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
       
        $scope.getTeamMembers = function(team){
            TeamService.getTeamMembers(team, $scope.currentUser.username)
                .then(function(response){
                    $scope.currentTeamMembers = response.data;
                });
        };
        
        $scope.getTeamMessageLog = function(team){
            MessageService.getMessageLogByTeam(team)
                .then(function(response){
                    var messageLog = [];
                    for(var i = 0; i < response.data.length; i++){
                        messageLog.push(
                            {text: response.data[i].text, date:response.data[i].timestamp, 
                            sender: response.data[i].sender.login, avatar:'img/profile-icon.png'});
                    }    
                    $scope.log = messageLog;
            });
        };
        
        $scope.getUserMessageLog = function(user){
            console.log(user);
            MessageService.getMessageLogByUser($scope.currentUser.username, user)
                .then(function(response){
                    var messageLog = [];
                    for(var i = 0; i < response.data.length; i++){
                        messageLog.push(
                            {text: response.data[i].text, date:response.data[i].timestamp, 
                            sender: response.data[i].sender.login, avatar:'img/profile-icon.png'});
                    }   
                    $scope.log = messageLog;
                });
        };
}]);
