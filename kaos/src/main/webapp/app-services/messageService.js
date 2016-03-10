'use strict';

var app = angular.module('app');

// Representing the remote RESTful 
app.factory('MessageService', ['$http',
    function($http) {

        var url = "http://localhost:8080/kaos/webresources/messages";

        return {
            //methods to call backend resource class (using AJAX) 
            
            getMessageLogByTeam: function(team){
                return $http.get(url + "/" + team);
            },
            sendMessageToTeam: function(team, sender, message){
                //Message is javascript object containing text, sender and date
                return $http.post(url + "/team/" + team, {sender: sender, message: message});
            },
            getMessageLogByUser: function(user1, user2){
                return $http.get(url + "/user/" + user1 + "/" + user2);
            },
            sendMessageToUser: function(receiver, sender, message){

                //Message is javascript object containing text, sender and date
                return $http.post(url + "/user", {receiver: receiver, sender: sender, message: message});
            }
       };
   }
]);
