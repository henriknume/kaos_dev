'use strict';

//This service is used fot creating and managing teams
angular.module('app').factory('TeamService', ['$http',
    function($http) {

        var url = "http://localhost:8080/kaos/webresources/teams";

        return {
            getTeamByName: function(team) {
                return $http.post(url + "/join", team);
            },
            getTeamMembers: function(team, user){
                return $http.get(url + "/members/" + team + "/" + user);
            },
            createTeam: function(team, user) {
                return $http.post(url, {team_name: team.team_name, password: team.password, user: user});
            }
        };
    }]);

