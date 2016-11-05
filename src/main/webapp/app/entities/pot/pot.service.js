(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('Pot', Pot);

    Pot.$inject = ['$resource', 'DateUtils'];

    function Pot ($resource, DateUtils) {
        var resourceUrl =  'public/pots/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.data = DateUtils.convertDateTimeFromServer(data.data);
                    }
                    return data;
                }
            },
            'getLast': {
            	method: 'GET',
            	url: 'public/pots/last',
                /*transformResponse: function (data) {
                    if (data) {
                    	console.log(data);
                        data = angular.fromJson(data);
                        data.data = DateUtils.convertDateTimeFromServer(data.data);
                    }
                    return data;
                }*/
            },
            'pagament': { url: 'api/pots/pagament', method: 'POST' },
            'extreure': {url: 'api/pots/extreure', method: 'POST' }
        });
    }
})();
