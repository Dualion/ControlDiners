(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .service('QuantitatService', QuantitatService);

    QuantitatService.$inject = ['$uibModal'];

    function QuantitatService ($uibModal) {

        this.open = function () {
            return $uibModal.open({
                animation: true,
                templateUrl: 'app/services/quantitat/quantitat.html',
                controller: 'NovaQuantitatController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'sm',
                resolve: {
                    entity: function () {
                        return {
                            diners: null,
                            actiu: false,
                            id: null
                        };
                    },
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('quantitat');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).result;
        }
    }
})();
