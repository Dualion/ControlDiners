(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('DinersPot', DinersPot);

    DinersPot.$inject = ['$resource', 'DateUtils'];

    function DinersPot ($resource, DateUtils) {
        var resourceUrl =  'public/diners-pots/:id';

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
            	url: 'public/diners-pots/last'
            },
            'pagament': { url: 'api/diners-pots/pagament', method: 'POST' },
            'extreure': {url: 'api/diners-pots/extreure', method: 'POST' },
            'cancelarPagament': {url: 'api/diners-pots/cancelarpagament', method: 'POST'}
        });
    }
})();
