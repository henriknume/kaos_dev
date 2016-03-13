'use strict';

angular.module('chat', []).controller('ChatController', 
    ['$scope', '$rootScope', 'UserService', 'TeamService', 'MessageService',
    function($scope, $rootScope, UserService, TeamService, MessageService){                
        
        $scope.chatStatus = 'none';//Used for knowing whether user is in chat chanel or not 
        
        $scope.currentUser = $rootScope.globals.currentUser;
        
        $scope.currentTeam = null;//The team chanel currentUser is in right now
        
        $scope.currentTeamMembers = null;//List of members of the team chanel currentUser is in right now  
        
        $scope.teams = [];  //List of all teams currentUser has joined                 
        
        $scope.chat_title = "";//Title on top of chat log                               
        
        $scope.currentPrivate = null;//The name of the user that currentUser is chatting with       
        
        $scope.log = [{ //List of all chat messages
            text: 'Welcome ' + $scope.currentUser.username + '!', 
            date: new Date(), sender: "System", avatar: 'img/system.png'
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
                confirm("An error occurred: " + response.message);
            });        
            
        //Start polling. Updates every 5 sec.
        setInterval(function(){
            if($scope.chatStatus === "team"){
                $scope.log = $scope.updateTeamMessageLog($scope.currentTeam);
            }else if($scope.chatStatus === "private"){
                $scope.log = $scope.updateUserMessageLog($scope.currentPrivate);                
            }
        }, 1000*5);
        
        //This function is called when the user selects a team chat
        $scope.enterChatRoom = function(team){
            $scope.chatStatus = "team";
            $scope.currentTeam = team;
            $scope.updateTeamMembers(team);
            $scope.chat_title = "#" + team;
            $scope.updateTeamMessageLog(team);
        };
        
        //This function is called when the user selects a private chat
        $scope.enterPrivateChatRoom = function(member){
            $scope.chatStatus = "private";
            $scope.currentPrivate = member.login;
            $scope.chat_title = "#" + $scope.currentTeam + ": " + member.login;
            $scope.updateUserMessageLog(member.login);            
        };
        
        //This function is called when the user submits text throught the chat box
        $scope.writeMessage = function(text){  
            if($scope.chatStatus === "team"){
                MessageService.sendMessageToTeam($scope.currentTeam, $scope.currentUser.username, text)
                    .then(function(){$scope.updateTeamMessageLog($scope.currentTeam);});
            }else if($scope.chatStatus === "private"){
                MessageService.sendMessageToUser($scope.currentPrivate, $scope.currentUser.username, text)
                    .then(function(){$scope.updateUserMessageLog($scope.currentPrivate);});  
            }else if($scope.chatStatus === "none"){
                $scope.log.push({   
                    text: "You are not in a team chat!", date: new Date(),
                    sender: "System", avatar: 'img/system.png'
                });
            }
            document.getElementById("myForm").reset();
        };    
       
        $scope.updateTeamMembers = function(team){
            TeamService.getTeamMembers(team, $scope.currentUser.username)
                .then(function(response){
                    $scope.currentTeamMembers = response.data;
                });
        };
        
        $scope.updateTeamMessageLog = function(team){
            MessageService.getMessageLogByTeam(team)
                .then(function(response){
                    var messageLog = [];
                    for(var i = 0; i < response.data.length; i++){
                        messageLog.push(
                            {text: response.data[i].text, date:response.data[i].timestamp, 
                            sender: response.data[i].sender.login, avatar:'img/profile-icon.png'});
                    }   
                    if(messageLog.length === 0){ //Default message
                        $scope.log = [{
                        text: 'Welcome to the chat chanel for ' + team,
                        date: new Date(), sender: 'System', avatar: 'img/system.png'             
                        }];
                    }else
                        $scope.log = messageLog.reverse();
            });
        };
        
        $scope.updateUserMessageLog = function(user){
            MessageService.getMessageLogByUser($scope.currentUser.username, user)
                .then(function(response){
                    var messageLog = [];
                    for(var i = 0; i < response.data.length; i++){
                        messageLog.push(
                            {text: response.data[i].text, date:response.data[i].timestamp, 
                            sender: response.data[i].sender.login, avatar:'img/profile-icon.png'});
                    }                                         
                    if(messageLog.length === 0){ //Default message
                        $scope.log = [{    
                            text: 'Welcome to the chat chanel for ' + user,
                            date: new Date(), sender: "system", avatar: 'img/system.png'         
                        }];
                    }else
                        $scope.log = messageLog.reverse();
                });
        };
}]);
