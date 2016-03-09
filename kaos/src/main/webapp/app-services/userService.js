'use strict';

var app = angular.module('app');

// Representing the remote RESTful 
app.factory('UserService', ['$http',
    function($http) {

        var url = "http://localhost:8080/kaos/webresources/users";

        return {
            //methods to call backend resource class (using AJAX) 
           
            /*findAll: function() {
                return $http.get(url);
            },*/
            getUserByLogin: function(user) { //Returns username, email, password
                return $http.post(url + "/login", user);
            },
            getTeamsByLogin: function(login){
                return $http.get(url + "/teams/" + login);
            },
            /*update: function(id, user) {
                return $http.put(url + "/" + id, user);
            },*/
            createUser: function(user) {
                return $http.post(url, user);
            },
            delete: function(user) {
                return $http.delete(url + "/" + user);
            },
            countUsers: function() {
                return $http.get(url + "/count");
            }
        };
    }]);

