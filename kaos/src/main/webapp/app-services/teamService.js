'use strict';

angular.module('app').factory('TeamService', ['$http',
    function($http) {

        var url = "http://localhost:20074/kaosdev/webresources/teams";

        return {
            //methods to call backend resource class (using AJAX) 
           
            /*findAll: function() {
                return $http.get(url);
            },*/
            /*getTeamByName: function(team) {
                return $http.get(url + "/" + team);
            },*/
            getMessageLogByTeam: function(team){
                return $http.get(url + "/log/" + team);
            },
            sendMessageToTeam: function(team, message){
                return $http.post(url + "/post/" + team + "/" + message);
            },
            /*update: function(id, team) {
                return $http.put(url + "/" + id, team);
            },*/
            getTeamMembers: function(team){
                return $http.get(url + "/members/" + team);
            },
            createTeam: function(team) {
                return $http.post(url, team);
            },
            /*delete: function(id) {
                return $http.delete(url + "/" + id);
            },*/
            countTeams: function() {
                return $http.get(url + "/count");
            }
        };
    }]);

