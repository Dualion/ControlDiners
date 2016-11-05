(function() {
    'use strict';
    angular
        .module('controlDinersApp')
        .factory('Quantitat', Quantitat);

    Quantitat.$inject = ['$resource'];

    function Quantitat ($resource) {
        var resourceUrl =  'api/quantitats/:id';

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
