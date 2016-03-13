'use strict';

var app = angular.module('app');
 
 //This service is used for sending and receiveing messages in the chat
app.factory('MessageService', ['$http',
    function($http) {

        var url = "http://localhost:8080/kaos/webresources/messages";

        return {            
            getMessageLogByTeam: function(team){
                return $http.get(url + "/" + team);
            },
            sendMessageToTeam: function(team, sender, message){
                return $http.post(url + "/team/" + team, {sender: sender, message: message});
            },
            getMessageLogByUser: function(user1, user2){
                return $http.get(url + "/user/" + user1 + "/" + user2);
            },
            sendMessageToUser: function(receiver, sender, message){
                return $http.post(url + "/user", {receiver: receiver, sender: sender, message: message});
            }
       };
   }
]);
