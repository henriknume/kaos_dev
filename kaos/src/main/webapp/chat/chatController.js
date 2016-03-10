'use strict';

//Here we define a module for our controllers. The array contains other 
//modules this one depends on. 
var chat = angular.module('chat', []);

chat.controller('ChatController', 
    ['$scope', '$rootScope', '$location', 'UserService', 'TeamService', 'MessageService',
    function($scope, $rootScope, $location, UserService, TeamService, MessageService){
        
        /*Here we can declare any variables or function we want to 
          access from the HTML page. */
         
        
        //Used for knowing whether user is in chat chanel or not. 
        $scope.chatStatus = 'none';
        
        $scope.currentUser = $rootScope.globals.currentUser;
        
        //The team chanel currentUser is in right now
        $scope.currentTeam = null;
        
        //List of members of the team chanel currentUser is in right now
        $scope.currentTeamMembers = null;
        
        //List of all teams currentUser has joined
        $scope.teams = [];       
        
        //Title string displayed in sidebar
        $scope.pm_title = "";        
                      
        //The name of the user that currentUser is chatting with
        $scope.currentPrivateChat = null;
        
        //List of all chat messages
        $scope.log = [{ 
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
                $scope.log = getTeamMessageLog($scope.currentTeam);
            }else if($scope.chatStatus === "private"){
                $scope.log = getUserMessageLog($scope.currentPrivateChat);                
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
            
            
            //Defauls message
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
            $scope.log = getUserMessageLog(member);
            
            //Defauls message
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
                MessageService.sendMessageToUser($scope.currentPrivateChat, $scope.currentUser, text);
                $scope.log = getUserMessageLog($scope.currentPrivateChat);
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
        
        $scope.deleteAcc = function(){
            var check = confirm("Are you shure you want to delete your account?");
            if(check === true){
                /*UserService.Delete($rootScope.globals.currentUser);
                $location.path("#/login");*/
            }
        };
        
        //***Helper functions used for calling services****//
       
        var getTeams = function(username){
            UserService.getTeamsByLogin(username)
                .success(function(response){
                    return response.value;
                }).error(function(response){                    
                    confirm("An error occurred: " + response.message);
                    return [];
                });
        };
        
        $scope.getTeamMembers = function(team){
            TeamService.getTeamMembers(team)
                .then(function(response){
                    console.log(response.data);
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
        
        var getUserMessageLog = function(user){
            MessageService.getMessageLogByUser($scope.currentUser, user)
                .success(function(response){
                    var messageLog = [];
                    for(var i = 0; i < response.length; i++){
                        messageLog.push(
                                {text: response[i].text, date:response[i].timestamp, 
                            sender: response[i].sender.login, avatar:'img/profile-icon.png'});
                    }                    
                    return messageLog;
                }).error(function(response){                    
                    confirm("An error occurred: " + response.message);
                    return [];
                });
        };
}]);
