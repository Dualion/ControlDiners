(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('UsuarisProces', UsuarisProces);

    UsuarisProces.$inject = ['$resource'];

    function UsuarisProces ($resource) {
        var resourceUrl =  'api/usuaris-proces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
