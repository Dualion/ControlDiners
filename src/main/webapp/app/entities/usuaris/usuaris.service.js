(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('Usuaris', Usuaris);

    Usuaris.$inject = ['$resource', 'DateUtils'];

    function Usuaris ($resource, DateUtils) {
        var resourceUrl =  'api/usuarises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataInici = DateUtils.convertDateTimeFromServer(data.dataInici);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
