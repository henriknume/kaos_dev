'use strict';

//Used for creating short messages saying 'success' or 'error' 
//that show up at the top of the page
angular.module('app').factory('FlashService', ['$rootScope', 
    function FlashService($rootScope){
        
        var service = {            
            InitService: function(){
                $rootScope.$on('$locationChangeStart', function () {
                    clearFlashMessage();
                });

                function clearFlashMessage() {
                    var flash = $rootScope.flash;
                    if (flash) {
                        if (!flash.keepAfterLocationChange) {
                            delete $rootScope.flash;
                        } else {
                            // only keep for a single location change
                            flash.keepAfterLocationChange = false;
                        }
                    }
                }
            },                    
            Success: function(message, keepAfterLocationChange) {
                $rootScope.flash = {
                    message: message,
                    type: 'success', 
                    keepAfterLocationChange: keepAfterLocationChange
                };
            },
            Error: function (message, keepAfterLocationChange) {
                $rootScope.flash = {
                    message: message,
                    type: 'error',
                    keepAfterLocationChange: keepAfterLocationChange
                };
            }            
        };
        service.InitService();
        return service;        
    }
]);