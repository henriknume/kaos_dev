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
            sendMessageToTeam: function(team, message){
                //Message is javascript object containing text, sender and date
                return $http.post(url + "/" + team, message);
            },
            getMessageLogByUser: function(user){
                return $http.get(url + "/" + user);
            },
            sendMessageToUser: function(user, message){
                //Message is javascript object containing text, sender and date
                return $http.post(url + "/" + user, message);
            }
       };
   }
]);
