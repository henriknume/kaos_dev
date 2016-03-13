'use strict';

// This service is used for creating and managing users
 angular.module('app').factory('UserService', ['$http',
    function($http) {

        var url = "http://localhost:8080/kaos/webresources/users";

        return {
            getUserByLogin: function(user) {
                return $http.post(url + "/login", user);
            },
            getTeamsByLogin: function(login){
                return $http.get(url + "/teams/" + login);
            },
            createUser: function(user) {
                return $http.post(url, user);
            }
        };
    }
]);

