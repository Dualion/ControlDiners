(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('Pot', Pot);

    Pot.$inject = ['$resource', 'DateUtils'];

    function Pot ($resource, DateUtils) {
        var resourceUrl =  'api/pots/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
