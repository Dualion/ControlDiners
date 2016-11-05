(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('Usuaris', Usuaris);

    Usuaris.$inject = ['$resource', 'DateUtils'];

    function Usuaris ($resource, DateUtils) {
        var resourceUrl =  'public/usuarises/:id';

        return $resource(resourceUrl, {}, {
            'query': {url: 'public/usuarises/:id',  method: 'GET', isArray: true},
            'get': {
            	url: 'public/usuarises/:id',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataInici = DateUtils.convertDateTimeFromServer(data.dataInici);
                    }
                    return data;
                }
            },
            'update': {url: 'api/usuarises/:id', method:'PUT' },
            'save': {url: 'api/usuarises/:id', method:'POST' }
        });
    }
})();
