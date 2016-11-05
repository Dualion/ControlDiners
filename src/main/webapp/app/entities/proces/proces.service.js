(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('Proces', Proces);

    Proces.$inject = ['$resource', 'DateUtils'];

    function Proces ($resource, DateUtils) {
        var resourceUrl =  'api/proces/:id';

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
            'getProcesIsActive': {url: 'public/proces/actiu'},
            'crearProces': {url: 'api/proces', method: 'POST'},
            'acabarProces': {url: 'api/proces/terminate', method: 'POST'}
        });
    }
})();
