'use strict';


var app = angular.module('app');

// Representing the remote RESTful 
app.factory('UserService', ['$http',
    function($http) {

        var url = "http://localhost:20074/kaosdev/webresources/users";

        return {
            //methods to call backend resource class (using AJAX) 
           
            /*findAll: function() {
                return $http.get(url);
            },*/
            getUserByLogin: function(login) { //Returns username, email, password
                return $http.get(url + "/" + login);
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
            /*delete: function(id) {
                return $http.delete(url + "/" + id);
            },*/
            countUsers: function() {
                return $http.get(url + "/count");
            }
        };
    }]);

