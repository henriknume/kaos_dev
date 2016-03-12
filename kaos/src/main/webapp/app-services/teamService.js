'use strict';

angular.module('app').factory('TeamService', ['$http',
    function($http) {

        var url = "http://localhost:8080/kaos/webresources/teams";

        return {
            //methods to call backend resource class (using AJAX) 
           
            /*findAll: function() {
                return $http.get(url);
            },*/
            getTeamByName: function(team) {
                return $http.post(url + "/join", team);
            },
            /*update: function(id, team) {
                return $http.put(url + "/" + id, team);
            },*/
            getTeamMembers: function(team, user){
                return $http.get(url + "/members/" + team + "/" + user);
            },
            createTeam: function(team, user) {
                return $http.post(url, {team_name: team.team_name, password: team.password, user: user});
            },
            /*delete: function(id) {
                return $http.delete(url + "/" + id);
            },*/
            countTeams: function() {
                return $http.get(url + "/count");
            }
        };
    }]);

